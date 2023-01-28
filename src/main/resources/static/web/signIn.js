const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            email: "",
            contrasena: "",
            error: "",
            status: null,
            err: "",


        }
    },
    created() {

    },
    methods: {

        login() {
            axios.post('/api/login', `email=${this.email}&password=${this.contrasena}`)
                .then(() => {
                    axios.get("/api/clients")
                        .then(() => {
                            location.href = "/manager.html"
                        })

                        .catch(() => {
                            location.href = "/web/accounts.html"
                        })

                })

                .catch(() => {
                    this.error = "Los datos ingresados son incorrectos"
                    setTimeout(() => this.error = " ", 3000)
                })


        },
    },
})
app.mount("#app")