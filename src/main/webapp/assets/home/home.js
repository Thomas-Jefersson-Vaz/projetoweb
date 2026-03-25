/**
 * Type location
 * `Location = {
 *      id: number,
 *      name: string,
 *      country: string,
 *      continent: string,
 *      description: string,
 *      price: number
 * `
 * @returns {Promise<Location[]>}
 */

async function getLocations() {
    try {
        const response = await fetch("/api/locations");
        console.log(response);
        // Verifica se a resposta foi bem sucedida (status 200-299)
        if (!response.ok) {
            throw new Error(`Erro de HTTP! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error("Erro ao buscar os destinos:", error);
        // Retorna um array vazio em caso de erro para não quebrar o layout
        return [];
    }
}

/**
 * Reserva destino pelo ID
 *
 * O ID do usuario fica no JWT
 * @param id
 * @returns {Promise<any>}
 */
async function bookDestination(id) {
    const response = await fetch(`/api/destination/book/${id}`, {method: "POST"});
    if (!response.ok) {
        throw new Error(`Erro ao agendar o destino ${id}`);
    }
    const json = await response.json();
    console.log(json);
    return json
}

/**
 * Renderiza os cartões dos destinos vindos da API
 * @returns {Promise<void>}
 */
function renderDestinations(locations) {
    const container = document.querySelector(".destinations-grid");

    container.innerHTML = "";

    if(locations.length === 0){
        container.innerHTML = "<p>Nenhum destino encontrado</p>"
        return
    }

    locations.forEach(location => {
        const div = `
            <div class="dest-card">
                <img src="${location.imageUrl}" alt="${location.name}" class="dest-img">
                <div class="dest-info">
                    <span class="dest-tag">${location.continent}</span>
                    <h3>${location.name}, ${location.country}</h3>
                    <p>${location.description}</p>
                    <div class="dest-footer">
                        <span class="price">A partir de <strong>${location.price}</strong></span>
                        <button class="btn-book" onclick="bookDestination('${location.id}')">Reservar</button>
                    </div>
                </div>
            </div>
        `
        container.insertAdjacentHTML("beforeend", div);
    })
}
async function init(){
    const locations = await getLocations();
    renderDestinations(locations);
}

/**
 * Pesquisa de acordo com o nome, data de inicio, e data de fim
 * No request deve retornar o tipo `Location[]`
 * @returns {Promise<void>}
 */
async function search(){
    const name = document.getElementById("search-name").value;
    const startDate = document.getElementById("search-start-date").value;
    const endDate = document.getElementById("search-end-date").value;

    try{
        const url = new URL("/api/search")
        url.searchParams.append("name", name);
        url.searchParams.append("startDate", startDate)
        url.searchParams.append("endDate", endDate)
        const response = await fetch(url.toString(), {method: "POST"});
        if (!response.ok) {
            throw new Error(`Erro ao buscar os destinos:", response.status);`)
        }
        const json = await response.json();
        renderDestinations(json);
    }catch (error) {
        console.error(error);
        renderDestinations([]);
    }
}

document.addEventListener("DOMContentLoaded", init);