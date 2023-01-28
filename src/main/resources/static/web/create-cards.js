const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            type: "",
            color: "",
            msj: "",
            accounts:"",
            accountChoose:"",
            json: [],


        }
    },
    created() {
        this.cargarDatos()


    },
    methods: {
        cargarDatos() {
            axios.get("/api/clients/current/accounts")
                .then(response => {
                    this.json = response.data;
                    console.log(this.json);
                    this.accounts = this.json.map(account => account.number);
                    console.log(this.accounts);
                })
            },

        createCard() {
            axios.post("/api/clients/current/cards", `type=${this.type}&color=${this.color}&account=${this.accountChoose}`)
                .then(e => {
                    console.log(e);

                    Swal.fire({
                        icon: 'success',
                        title: 'ok!',
                        text: 'Card created successfully!'
                    })
                    setTimeout(() => location.href = "/web/cards.html", 1000)

                })

                .catch(e => {
                    this.error = e.response.data
                    console.log(e)

                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: this.error,
                    })
                });
                
            // } else if(this.type == ""){
            // Swal.fire({
            // icon: 'error',
            // title: 'Oops...',
            // text: this.error,
            // })
            
            // .catch(() => Swal.fire({
            // icon: 'error',
            // title: 'Oops...',
            // text: 'you cannot apply for the same card!',

            // }))
        },

        salir() {
            axios.post('/api/logout')
                .then(() => location.href = "/web/index.html")
        }

    },

})
app.mount("#app")