# One-Way Traffic Lights

This statechart shows a single traffic signal with operational control.

---
### Requirements Covered

- **R4**: The signal follows the UK traffic-light sequence **":r >> :ra >> :g >> :a >> :r"**.
- **R6**: Signal colours change only in response to **":next"**.
- **R7**: The system starts in the **"Stopped"** (all-red) state.
- **R9**: A **":run"** event transitions the system from the all-red state into normal operation.
- **R10**: A **":halt"** event causes the system to safely return to the all-red state.

---
```mermaid
stateDiagram-v2

    [*] --> stopped

    stopped --> r : run

    r --> ra : next
    ra --> g : next
    g --> a : next
    a --> r : next

    r --> stopped : halt
    ra --> halting_ra : halt
    g --> halting_g : halt
    a --> halting_a : halt

    halting_ra --> g : next
    halting_g --> a : next
    halting_a --> stopped : next
```