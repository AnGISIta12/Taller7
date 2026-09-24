# PoC DevOps: despliegue sin caída con k3s frente a Docker Compose

| | |
|---|---|
| **Rol** | DevOps |
| **ADR que respalda** | ADR-011 (k3s en VM3, Docker Compose en el resto de VMs) |
| **Escenarios del SAD relacionados** | AC5-E2 (mantenimiento planeado), AC5-E3 (caída de un microservicio), AC7-E1 (pipeline de CI), AC2-E5 (uso de recursos), RNF-08 (reversión del despliegue) |
| **Estado** | Planeada. **Los resultados de este documento son estimados, no medidos.** Se reemplazan por mediciones reales al ejecutar la prueba |

---

## 1. Pregunta

¿Se justifica usar k3s para los 8 microservicios en VM3, o bastaba con Docker Compose como en las demás VMs?

ADR-011 eligió k3s por dos razones: actualizar un servicio sin que deje de responder (rolling update) y reiniciarlo solo si falla (auto-healing). El costo aceptado es más complejidad y más consumo de recursos en una VM con capacidad fija (K10). Esta prueba busca cuantificar las dos cosas.

## 2. Hipótesis

- **H1:** al actualizar un servicio bajo carga, k3s no pierde peticiones y Docker Compose sí, porque Compose detiene el contenedor viejo antes de que el nuevo esté listo.
- **H2:** revertir un despliegue malo es más rápido y seguro con k3s (`kubectl rollout undo`) que con Compose, donde hay que volver a desplegar la imagen anterior.
- **H3:** el costo de k3s en recursos cabe en la reserva de VM3 del Documento de Infraestructura (0.5 vCPU y 2 GiB para el sistema).

## 3. Método

1. **Servicio de prueba:** una API HTTP mínima con un endpoint `/health` y un endpoint de negocio simulado. Se construyen dos versiones, `v1` y `v2`.
2. **Dos montajes con la misma máquina y la misma imagen:**
   - Docker Compose, con una réplica y `restart: always`.
   - k3s, con 2 réplicas, `readinessProbe` sobre `/health` y estrategia de rolling update con `maxUnavailable: 0` y `maxSurge: 1`.
3. **Carga:** k6 con 50 usuarios virtuales durante 2 minutos, lo que equivale a la carga esperada en hora pico de AC2-E4.
4. **Pruebas bajo carga:**
   - Actualizar de `v1` a `v2` a mitad de la prueba.
   - Matar el proceso del servicio y medir cuánto tarda en volver a responder.
   - Revertir de `v2` a `v1`.
5. **Consumo:** CPU y RAM del plano de control de k3s en reposo, con `kubectl top` y `docker stats`.
6. **Repeticiones:** 5 por prueba; se reporta la mediana.

Los scripts (manifiestos de k3s, `docker-compose.yml` y script de k6) van en `tests/performance/poc-k3s-vs-compose/`.

## 4. Resultados estimados

**Estos valores son estimados, no medidos.** Salen del funcionamiento documentado de cada herramienta y de la configuración del método. La columna "Base del estimado" explica de dónde sale cada uno.

| Medida | Docker Compose (estimado) | k3s (estimado) | Base del estimado |
|---|---|---|---|
| Peticiones fallidas al actualizar, bajo 50 usuarios | 1% a 5% | 0% | Compose detiene el contenedor antes de arrancar el nuevo, lo que deja de 2 a 10 s sin servicio. k3s no retira un pod hasta que el nuevo pasa la `readinessProbe` |
| Tiempo sin servicio al actualizar | 2 a 10 s | 0 s | Mismo motivo |
| Duración total de la actualización | 5 a 15 s | 20 a 40 s | k3s es más lento porque espera que cada pod nuevo esté listo antes de retirar el viejo |
| Recuperación tras matar el proceso | 2 a 5 s sin servicio | 0 s sin servicio | Con una sola réplica, Compose queda caído mientras reinicia. Con 2 réplicas, k3s sigue atendiendo con la otra |
| Tiempo de reversión | 5 a 15 s, con caída | 15 a 30 s, sin caída | Compose vuelve a desplegar la imagen anterior; k3s usa `kubectl rollout undo` con la misma estrategia de rolling update |
| RAM del plano de control en reposo | No aplica | 500 a 800 MiB | Consumo típico de un servidor k3s de un nodo, según su documentación |
| CPU del plano de control en reposo | No aplica | 0.1 a 0.3 vCPU | Mismo motivo |

## 5. Conclusión preliminar

Si las mediciones confirman los estimados:

- **H1 se cumple:** k3s actualiza sin caída y Compose no. Justifica ADR-011 para VM3, donde viven los 8 servicios y se despliegan por separado varias veces por sprint.
- **H2 se cumple en seguridad, no en velocidad:** k3s revierte más lento, pero sin caída. Para RNF-08 importa más no afectar a los usuarios que la rapidez.
- **H3 se cumple:** el plano de control de k3s (hasta 0.8 GiB y 0.3 vCPU estimados) cabe dentro de la reserva de 2 GiB y 0.5 vCPU de VM3.
- **El costo es real:** cada actualización tarda más, y k3s ocupa recursos que en Compose quedarían libres. Por eso no se usa en las VMs que corren un solo componente.

## 6. Qué falta para convertir esto en resultado

1. Ejecutar el método de la sección 3 y reemplazar la tabla de la sección 4 por las medianas medidas.
2. Guardar los scripts y el reporte de k6 en el repositorio.
3. Si los datos contradicen una hipótesis, actualizar ADR-011 con el resultado real.
