const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            nombre: "",
            apellido: "",
            email: "",
            contrasena: "",
            error: "",


        }
    },
    created() {

    },
    methods: {

        registro() {
            axios.post('/api/clients', `firstName=${this.nombre}&lastName=${this.apellido}&email=${this.email}&password=${this.contrasena}`)
                .then(() => {
                    console.log("registro exitoso");
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
                })

                .catch(() => {
                    this.error = "Los datos ingresados no son correctos"
                })

        },
    },
})
app.mount("#app")