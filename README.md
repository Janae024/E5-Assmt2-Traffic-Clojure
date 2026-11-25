# CSCU9E5 Assignment 2 - Traffic Light Statecharts

## Overview
This project contains the 3 specific light specifications developed in clj-statecharts for CSCU9E5 Software Engineering 1.

The project structures and verifies the behavious of the traffic light system using statecharts, state diagrams, and automated tests that meets requirements.

---

## Artifacts

### 1. One-Way Traffic Lights Without Control
Files:

- `src/e5/one_way_without-ctrl.cljc`
- `test/e5/one_way_without_ctrl_test.cljc`
- `docs/one_way_without_ctrl.md`

Description:
A single traffic signal that continuously cycles throughout the UK traffic-light sequence
`:r >> :ra >> :g >> :a >> :r`

(This specification addresses requirements R1-R8.)

---

### 2. One-Way Traffic Lights
Files:

- `src/e5/one_way.cljc`
- `test/e5/one_way_test.cljc`
- `doc/one_way.md`

Description:
A single traffic signal that supports
- `:run` to begin operation
- `:halt` to stop operation safely

(This specification addresses requirements R1-R10.)

---

### Three-Way Traffic Lights
Files:

- `src/e5/three_way.cljc`
- `test/e5/three_way_test.cljc`
- `doc/three_way.md`

Description:
A coordinated three-way traffic system that has:
- West (`:w`)
- North (`:n`)
- South (`:s`)

Each signal follows the UK traffic-light sequence while ensuring smooth cycle and prevents conflicting green signals.

(This specification addresses requirements R1-R10.)

---

## Running the tests
From the project root directory:

```bash
clj -M:test
```

This executes all verification tests using Kaocha.

---
## Design Approach

The specifications were implemented using state machines provided by the clj-statecharts library.

Key design goals:

- Follow the UK traffic-light colour sequence.
- Ensure safe traffic flow.
- Support proper distribution of scheduling between directions.
- Verify behaviour using automated tests.
- Keep specifications simple and easy to understand.