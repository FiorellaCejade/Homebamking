const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            json: [],
            loans: [],
            payments :[],
            accountsNumber :[],
            nameOfLoanChoose : "",
            id : 0,
            amount : "",
            paymentChoose : 0,
            accountChoose :0,
            paymentsLoan : [],
            loanChoose : [],
            idL : 0,
            quotaInteres : 0,
            totalApagar : 0,
            MaxAmount : 0,
            
        
        }
    },
    created() {
        this.ourLoans()
        this. cargarDatos()

    },
    methods: {

        ourLoans() {
            axios.get("/api/loans")
                .then(response => {
                    console.log(response);
                    this.loans = response.data;
                    })

                .catch(error => {
                    console.log(error)
                });
        },

        applyLoans() {
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
                    axios.post("/api/loans ",{ id: this.id, amount: this.amount, payment: this.paymentChoose, accounDestino : this.accountChoose})
                        .then(() => {
                            console.log(this.id);
                            Swal.fire(
                                'successful transfer!',
                            )
                            setTimeout(() => location.href = "/web/accounts.html", 1000)
                            
                        })
                        .catch(e => {
                            this.error = e.response.data
                            
                
                            if (this.amount == "") {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
                
                                })
                            } else if (this.paymentChoose == "") {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
                
                                })
                
                            } else if (this.accounDestino == "") {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: this.error,
                
                                })
                
                            } else if (this.amount > this.maxAmount) {
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

        
        salir(){
            axios.post('/api/logout')
            .then(() => location.href = "/web/index.html")
        },

        cargarDatos() {
            axios.get("/api/clients/current/accounts")
                .then(response => {
                    this.json = response.data;
                    this.accountsNumber = this.json.map(account => account.number)
                }
                )

                .catch(err => {
                    console.log();
                })
        },

    },

    computed :{

        filterPayments(){

            this.loanChoose = this.loans.find(loan => loan.name == this.nameOfLoanChoose);
            
            if(this.loanChoose){
                this.MaxAmount = this.loanChoose.maxAmount
                this.payments = this.loanChoose.payments;
                this.id = this.loanChoose.id;
                console.log(this.paymentChoose);

            }
        },

        montoApagar (){
            this.totalApagar = (this.amount * 1.20).toLocaleString();
            
        },

        quotaConInteres(){
            this.quotaInteres =(this.amount * 1.20 / this.paymentChoose);
        }


    }

})
app.mount("#app")