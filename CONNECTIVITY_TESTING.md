# Network Connectivity Monitoring & Diagnostics

This module integrates connectivity observation and failure diagnostics based on [`ConnectivityAndInternetAccess.kt`](https://gist.github.com/rodrigosambadesaa/729cca29a031fef4e2f15751863b655f) into the Movies networking stack.

## Architecture & Design

Normal operation performs a **cheap local guard** before a new remote operation:

1. **Local policy**: the operation is allowed only when `isConnected(context)` and `hasPhysicalNetwork(context)` are both true. This prevents a dangling VPN-only network from starting a request.
2. **Passive observation**: an application-level `NetworkObserver` follows Android's default network state and normalizes it with the physical-transport check, without sending network traffic.
3. **Transparent execution**: Ktor/OkHttp and dynamic Coil resources execute their real operation directly after the local guard; redirects, CDNs and multiple hosts are not pre-enumerated.
4. **Failure-triggered active diagnostics**: active generic diagnosis is initiated only after a transport `IOException` such as a connection failure, DNS error or timeout.

HTTP response errors (such as 401, 404, or 500) are valid HTTP responses and do not trigger network diagnostics. The interceptor always rethrows the original transport exception unchanged.

## Testing & Logcat Inspection

Filter Logcat by tag:

```text
MoviesConnectivity
```

### Verification Scenarios

1. **Normal network connection**: Browse and search movies. The passive observer reports default network changes; no active diagnostics run.
2. **Network switching**: switch between Wi-Fi and Cellular. The observer emits updated states without active polling.
3. **VPN-only / AdGuard**: with VPN active and Wi-Fi/cellular/Ethernet disabled, `isConnected` may remain true but `hasPhysicalNetwork=false`; the normalized state is offline and new requests are not started.
4. **VPN recovery**: restore Wi-Fi or cellular while the VPN remains active. The observer publishes an available state and new requests are allowed again.
5. **Portal/validation**: `internetValidated` and `captivePortalDetected` remain separate passive Android signals; neither replaces the physical-network guard.
6. **Active Diagnostic Guards**: active diagnostics implement a 5-second cooldown and single-flight execution to prevent probe spam during burst request failures.
