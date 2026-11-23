# Three-Way Traffic Lights

This statechart shows the 3-coordinated traffic signals (west, north, and south) that operates fairly, and prevents conflicting green light, and safely transition between running and stopped states.

---

### Requirements Covered
- **R4**: The signal follows the UK traffic-light sequence **":r >> :ra >> :g >> :a >> :r"**.
- **R5**: No two signals can display green or amber at the same time.
- **R7**: The system starts in the all-red state.
- **R8**: West, north, and south signals each eventually receive a green phase in a fair rotation.
- **R9**: A **":run"** event starts the traffic-light cycle.
- **R10**: A **":halt"** event causes the system to return safely to the all-red state.

---

```mermaid

stateDiagram-v2

    [*] --> stopped

    stopped --> w_r : run

    %% West cycle
    w_r --> w_ra : next
    w_ra --> w_g : next
    w_g --> w_a : next
    w_a --> n_r : next

    %% North cycle
    n_r --> n_ra : next
    n_ra --> n_g : next
    n_g --> n_a : next
    n_a --> s_r : next

    %% South cycle
    s_r --> s_ra : next
    s_ra --> s_g : next
    s_g --> s_a : next
    s_a --> w_r : next

    %% Immediate halt from red states
    w_r --> stopped : halt
    n_r --> stopped : halt
    s_r --> stopped : halt

    %% Safe halt from west cycle
    w_ra --> w_halting_ra : halt
    w_g --> w_halting_g : halt
    w_a --> w_halting_a : halt

    w_halting_ra --> w_g : next
    w_halting_g --> w_a : next    
    w_halting_a --> stopped : next

    %% Safe halt from north cycle    
    n_ra --> n_halting_ra : halt
    n_g --> n_halting_g : halt   
    n_a --> n_halting_a : halt

    n_halting_ra --> n_g : next
    n_halting_g --> n_a : next
    n_halting_a --> stopped : next

    %% Safe halt from south cycle
    s_ra --> s_halting_ra : halt    
    s_g --> s_halting_g : halt
    s_a --> s_halting_a : halt

    s_halting_ra --> s_g: next
    s_halting_g --> s_a : next
    s_halting_a --> stopped : next
```