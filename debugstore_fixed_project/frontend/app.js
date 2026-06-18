const API_BASE = "http://localhost:8080/api";

async function checkHealth() {
    const result = document.getElementById("healthResult");

    try {
        const res = await fetch(`${API_BASE}/health`);
        result.innerText = await res.text();
    } catch (err) {
        result.innerText = "Backend not connected";
    }
}

async function loadProducts() {
    const container = document.getElementById("products");
    container.innerHTML = "Loading...";

    try {
        const res = await fetch(`${API_BASE}/products`);
        const products = await res.json();

        container.innerHTML = products.map(p => `
            <div class="product">
                <h3>${p.name}</h3>
                <p>Price: ₹${p.price}</p>
                <p>Quantity: ${p.quantity}</p>
                <button onclick="deleteProduct(${p.id})">Delete</button>
            </div>
        `).join("");
    } catch (err) {
        container.innerHTML = "Failed to load products";
    }
}

async function addProduct() {
    const name = document.getElementById("name").value;
    const price = Number(document.getElementById("price").value);
    const quantity = Number(document.getElementById("quantity").value);

    const body = {
        name,
        price,
        quantity
    };

    const res = await fetch(`${API_BASE}/products`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    });

    if (!res.ok) {
        alert(await res.text());
        return;
    }

    loadProducts();
}

async function deleteProduct(id) {
    await fetch(`${API_BASE}/products/${id}`, {
        method: "DELETE"
    });

    loadProducts();
}

async function searchProducts() {
    const query = document.getElementById("searchBox").value;

    const res = await fetch(`${API_BASE}/products/search?q=${encodeURIComponent(query)}`);
    const products = await res.json();

    const container = document.getElementById("products");
    container.innerHTML = products.map(p => `
        <div class="product">
            <h3>${p.name}</h3>
            <p>Price: ₹${p.price}</p>
            <p>Quantity: ${p.quantity}</p>
            <button onclick="deleteProduct(${p.id})">Delete</button>
        </div>
    `).join("");
}

loadProducts();