// URL deines Backends
const API_URL = "http://localhost:8081/product-api/products";

async function loadProducts() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error(`HTTP-Error ${response.status}`);
        }
        const products = await response.json();
        renderProducts(products);
    } catch (error) {
        console.error("Fehler beim Laden der Produkte:", error);
        document.getElementById("product-list").innerHTML = `
            <div class="alert alert-danger">Fehler beim Laden der Produkte.</div>
        `;
    }
}

function renderProducts(products) {
    const container = document.getElementById("product-list");
    container.innerHTML = "";

    if (products.length === 0) {
        container.innerHTML = `<p class="text-muted">Keine Produkte verfügbar.</p>`;
        return;
    }

    for (const p of products) {
        const card = document.createElement("div");
        card.className = "col-md-4";

        card.innerHTML = `
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h5 class="card-title">${p.name}</h5>
                    <p class="card-text text-muted mb-1">ID: ${p.id}</p>
                    <p class="fw-bold">${p.price} ${p.currency}</p>
                    <button class="btn btn-primary mt-auto" data-id="${p.id}">In den Warenkorb</button>
                </div>
            </div>
        `;
        container.appendChild(card);
    }
}

// Beim Laden der Seite Produkte holen
document.addEventListener("DOMContentLoaded", loadProducts);
