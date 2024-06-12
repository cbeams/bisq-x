rootProject.name = "bisq-x"
include(
    "apps:cli",
    "apps:desktop",
    "apps:daemon",
    "client:oas",
    "core:oas",
    "core:node",
    "core:domain",
    "core:domain:trading",
    "core:network",
    "core:network:http",
    "core:network:p2p",
    "core:logging",
    "core:api",
    "core",
    "demos:dca",
)
