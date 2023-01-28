$('.open-menu-btn').on('click', function () {
    if ($('body').hasClass('closed-menu')) {
        $('body').removeClass('closed-menu');
    } else $('body').addClass('closed-menu');
});

const { createApp } = Vue;

const app = createApp({
    data() {
        return {
            accounts: [],
            json: [],
            clientLoan: [],
            cards: [],
            account:[],
            error:"",
            show_account: true,
            typeAccount:"",
            


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
                    this.accounts = this.json.account
                    this.clientLoan = this.json.clientLoan;
                    this.cards = this.json.card;
                    this.accounts.sort((a, b) => {
                        if (a.id < b.id) {
                            return -1
                        }
                    })
                })
        },
        salir(){
            axios.post('/api/logout')
            .then(() => location.href = "/web/index.html")
        },

        deleteAccount(id) {
    
            axios.patch(`/api/clients/current/account/${id}`)
            .then(()=> {
                this.account = this.accounts.filter(account => account.id == id);
                this.show_cards = this.account[0].show_card;
                if (this.show_account){
                    this.show_account=false;
                }
                location.href = "/web/accounts.html"
            })
            
            .catch(e => {
                console.log(e);
                this.error = e.response.data
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: this.error,
                })

                
                }
            )},

        createAccount(){
                axios.post('/api/clients/current/accounts',`accountType=${this.typeAccount}`)
                .then(() => location.href = "/web/accounts.html")
        }
    },
})
app.mount("#app")