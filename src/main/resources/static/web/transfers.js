const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            accounts: [],
            amount: 0,
            description: "",
            numberOrigen: "",
            numberDestino: "",
            type: "",
            accountFilter: [],
            error: "",
            json: [],
            number: "",
            balance: undefined,
            balanceAccount: undefined,




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
                    this.accounts = this.json.map(account => account.number)
                    this.balanceAccount = this.json.map(balanceAccount => balanceAccount.balance)






                }
                )
        },

        createTransfer() {

            Swal.fire({
                title: 'Are you sure?',
                text: "Are you sure to make the transfer?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, transfer!'
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/transactions',`amount=${this.amount}&description=${this.description}&numberOrigen=${this.numberOrigen}&numberDestino=${this.numberDestino}`)
                        .then(() => {

                            Swal.fire(
                                'successful transfer!',
                            )

                            location.href = "/web/accounts.html"
                        })

                        .catch(e => {
                            this.error = e.response.data
                            
        
                            if (this.numberOrigen == "") {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
        
                                })
                            } else if (this.numberDestino == "") {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
        
                                })
        
                            } else if (this.amount == 0) {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
        
                                })
        
                            } else if (this.description == "") {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
                                })
                            } else if (this.numberOrigen == this.numberDestino) {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
                                })
                            } else if (this.numberOrigen == "") {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
                                })
        
                            } else if (this.balanceAccount < this.amount) {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
                                })
        
                            } else if (this.numberDestino == null) {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
                                })
                            }
                        }
        
                        )
                }
            })
                
        },

        salir() {
            axios.post('/api/logout')
                .then(() => location.href = "/web/index.html")
        }

    },

    computed: {

        cuentasOrigen() {

            this.accountFilter = this.accounts.filter(account => account != this.numberOrigen)

        }

    }

})
app.mount("#app")

