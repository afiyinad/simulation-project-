import React, { useState } from "react";
import Canvas from "./components/Canvas";
import Toolbar from "./components/Toolbar";

function App() {
  const [gates, setGates] = useState([]);
  const [wires, setWires] = useState([]);
  const [pendingWireFrom, setPendingWireFrom] = useState(null);

  const addGate = (type) => {
    const id = "g" + (gates.length + 1);

    const newGate = {
      id,
      type,
      x: 100,
      y: 100,
      inputs: type.startsWith("INPUT") ? [] : [null, null],
      output: type === "INPUT_1" ? true : false
    };

    setGates((prev) => [...prev, newGate]);
  };

  const handleGateDrag = (id, dx, dy) => {
    setGates((prev) =>
      prev.map((g) =>
        g.id === id ? { ...g, x: g.x + dx, y: g.y + dy } : g
      )
    );
  };

  const startWire = (fromGateId) => {
    setPendingWireFrom(fromGateId);
  };

  const endWire = (toGateId, inputIndex) => {
  if (!pendingWireFrom) return;

  const id = "w" + (wires.length + 1);

  const newWire = {
    id,
    from: pendingWireFrom,
    to: toGateId,
    inputIndex
  };

  setWires((prev) => [...prev, newWire]);
  setPendingWireFrom(null);
  };


  // ⭐ LOGIC ENGINE ⭐
  const simulateCircuit = () => {
    let updated = [...gates];

    // 1. Reset all gate inputs
    updated = updated.map((g) =>
      g.type.startsWith("INPUT")
        ? g
        : { ...g, inputs: [null, null] }
    );

    // 2. Push outputs through wires
    wires.forEach((wire) => {
      const fromGate = updated.find((g) => g.id === wire.from);
      const toGate = updated.find((g) => g.id === wire.to);

      if (!fromGate || !toGate) return;

      const out = fromGate.output;
      toGate.inputs[wire.inputIndex] = out;
    });

    // 3. Evaluate logic gates
    updated = updated.map((g) => {
      if (g.type === "AND") {
        return { ...g, output: g.inputs[0] && g.inputs[1] };
      }
      if (g.type === "OR") {
        return { ...g, output: g.inputs[0] || g.inputs[1] };
      }
      if (g.type === "XOR") {
        return { ...g, output: g.inputs[0] !== g.inputs[1] };
      }
      if (g.type === "NOT") {
        return { ...g, output: !g.inputs[0] };
      }
      return g; // INPUT nodes unchanged
    });

    setGates(updated);
  };

  return (
    <div style={{ width: "100vw", height: "100vh" }}>
      <Toolbar onAddGate={addGate} onSimulate={simulateCircuit} />
      <Canvas
        gates={gates}
        wires={wires}
        onGateDrag={handleGateDrag}
        startWire={startWire}
        endWire={endWire}
      />
    </div>
  );
}

export default App;
