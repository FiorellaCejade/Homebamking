const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            json: [],
            cards: [],
            debit: [],
            credit: [],
            type:"",
            color:"",
            id:0,
            show_cards:true,
            card:[],
            dateString:"",
            
        
        }
    },
    created() {
        this.cargarDatos()

    },
    methods: {

        cargarDatos() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.json = response.data;
                    this.cards = this.json.card;
                    this.filtrarTarjeta(this.cards);
                    this.dateString = new Date().getFullYear().toString;
                })
        },
        
        filtrarTarjeta(cards) {
            this.debit = cards.filter(cards => cards.type === 'DEBIT')
            this.credit = cards.filter(cards => cards.type === 'CREDIT')
        },

        deleteCard(id) {
            axios.patch(`/api/clients/current/cards/${id}`)
            .then(()=> {
                this.card = this.cards.filter(c => c.id == id);
                console.log(this.card);
                this.show_cards = this.card[0].show_card;
                this.amountCard = this.card[0].amount
                if (this.show_cards){
                    this.show_cards=false;
                }

                location.href = "/web/cards.html"

            })

            .catch(err => {
                console.log(err);
            });
        },
        
        salir(){
            axios.post('/api/logout')
            .then(() => location.href = "/web/index.html")
        }
    },
    
})
app.mount("#app")