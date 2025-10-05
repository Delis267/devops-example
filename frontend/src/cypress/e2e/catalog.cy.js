describe("Produktkatalog laden E2E", () => {
    beforeEach(() => {
        cy.intercept(
            "GET",
            "http://localhost:8081/product-api/products"
        ).as("getProducts");
    });

    it("lädt Produkte und rendert Produktkarten", () => {
        cy.visit("/");
        cy.wait("@getProducts", {timeout: 15000})
            .its("response.statusCode")
            .should("be.oneOf", [200]); // Backend muss 200 liefern

        // mindestens 1 Produktkarte vorhanden
        cy.get("#product-list .card", {timeout: 10000})
            .should("have.length.greaterThan", 0);

        // Titel sichtbar
        cy.get("#product-list .card .card-title").first().should("be.visible");

        // Preis + Währung richtig
        cy.get("#product-list .card").first().within(() => {
            cy.contains("EUR").should("exist");
        });

        // Button vorhanden
        cy.get("#product-list .card button")
            .first()
            .should("be.visible")
            .and("have.text", "In den Warenkorb");

        // erstelle Screenshot
        cy.screenshot("Produktkatalog-geladen");
    });
});
