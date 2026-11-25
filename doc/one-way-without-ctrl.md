# One-Way Traffic Lights Without Control

This statechart models a single traffic signal operating continuously using only **":next"** events.

---

```mermaid
stateDiagram-v2

    [*] --> r

    r --> ra : next
    ra --> g : next
    g--> a : next
    a --> r : next
```