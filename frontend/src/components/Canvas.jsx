import React from "react";

function Canvas({ gates, wires, onGateDrag, startWire, endWire }) {

  const handleMouseDown = (gate, e) => {
    let lastX = e.clientX;
    let lastY = e.clientY;

    const move = (ev) => {
      const dx = ev.clientX - lastX;
      const dy = ev.clientY - lastY;

      onGateDrag(gate.id, dx, dy);

      lastX = ev.clientX;
      lastY = ev.clientY;
    };

    const up = () => {
      window.removeEventListener("pointermove", move);
      window.removeEventListener("pointerup", up);
    };

    window.addEventListener("pointermove", move);
    window.addEventListener("pointerup", up);
  };

  return (
    <svg width="100%" height="100%" style={{ background: "#f0f0f0" }}>
      
      {/* Draw wires */}
      {wires.map((wire) => {
        const from = gates.find((g) => g.id === wire.from);
        const to = gates.find((g) => g.id === wire.to);

        if (!from || !to) return null;

        const inputY = wire.inputIndex === 0 ? 10 : 30;

        return (
          <line
            key={wire.id}
            x1={from.x + 60}
            y1={from.y + 20}
            x2={to.x}
            y2={to.y + inputY}
            stroke="black"
            strokeWidth="3"
          />
        );
      })}

      {/* Draw gates + input nodes */}
      {gates.map((gate) => (
        <g
          key={gate.id}
          transform={`translate(${gate.x}, ${gate.y})`}
          onPointerDown={(e) => handleMouseDown(gate, e)}
        >

          {/* INPUT nodes */}
          {gate.type.startsWith("INPUT") ? (
            <>
              <rect width="60" height="40" fill="#cce5ff" stroke="#000" />
              <text x="30" y="25" textAnchor="middle" fontSize="14">
                {gate.type === "INPUT_0" ? "0" : "1"}
              </text>

              {/* Output pin */}
              <circle
                cx={60}
                cy={20}
                r={6}
                fill={gate.output ? "green" : "red"}
                onPointerDown={(e) => {
                  e.stopPropagation();
                  startWire(gate.id);
                }}
              />
            </>
          ) : (
            <>
              {/* Logic gates */}
              <rect width="60" height="40" fill="#fff" stroke="#000" />
              <text x="30" y="25" textAnchor="middle" fontSize="14">
                {gate.type}
              </text>

              {/* Output pin */}
              <circle
                cx={60}
                cy={20}
                r={6}
                fill={gate.output ? "green" : "red"}
                onPointerDown={(e) => {
                  e.stopPropagation();
                  startWire(gate.id);
                }}
              />

              {/* Input A pin (index 0) */}
              <circle
                cx={0}
                cy={10}
                r={6}
                fill="red"
                onPointerDown={(e) => {
                  e.stopPropagation();
                  endWire(gate.id, 0);
                }}
              />

              {/* Input B pin (index 1) */}
              <circle
                cx={0}
                cy={30}
                r={6}
                fill="red"
                onPointerDown={(e) => {
                  e.stopPropagation();
                  endWire(gate.id, 1);
                }}
              />
            </>
          )}

        </g>
      ))}
    </svg>
  );
}

export default Canvas;
