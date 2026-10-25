import React from "react";

function Toolbar({ onAddGate, onSimulate }) {
  return (
    <div style={{
      display: "flex",
      gap: "10px",
      padding: "10px",
      background: "#ddd",
      borderBottom: "2px solid #aaa"
    }}>
      <button onClick={() => onAddGate("INPUT_0")}>Input 0</button>
      <button onClick={() => onAddGate("INPUT_1")}>Input 1</button>

      <button onClick={() => onAddGate("AND")}>AND Gate</button>
      <button onClick={() => onAddGate("OR")}>OR Gate</button>
      <button onClick={() => onAddGate("NOT")}>NOT Gate</button>
      <button onClick={() => onAddGate("XOR")}>XOR Gate</button>

      <button onClick={() => onAddGate("NAND")}>NAND Gate</button>
      <button onClick={() => onAddGate("NOR")}>NOR Gate</button>
      <button onClick={() => onAddGate("XNOR")}>XNOR Gate</button>


      <button style={{ marginLeft: "20px", background: "#4CAF50", color: "white" }}
              onClick={onSimulate}>
        Simulate
      </button>
    </div>
  );
}

export default Toolbar;
