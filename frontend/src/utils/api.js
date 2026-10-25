export async function runSimulation(gates, wires) {
  const res = await fetch("http://localhost:8080/simulate", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ gates, wires })
  });

  return await res.json();
}
