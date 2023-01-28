const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            clients:[],
            client:{},
            json :[],
            name:"",
            lastName:"",
            email:"",
            id:undefined,
            inputFirstName:"",
            inputLastName:"",
            inputemail:"",
            idCliente:undefined,
            newClient:{},
            nameLoan: '',
            maxAmount: undefined,
            arrPayments:[],
            percentaje: undefined,

        }
    },
    created (){
        this.cargarDatos()
        
    },
    methods:{

        cargarDatos(){
            axios.get("/rest/clients")
        .then(response => {
            this.json = response.data;
            this.clients = response.data._embedded.clients;

            

        })
                .catch(error => console.log(error))
        },
        captureDateClient() {

            if (this.name !== "" && this.lastName !== "" && this.email !== "") {

                this.client = {
                    firstName: this.name,
                    lastName: this.lastName,
                    email: this.email,
                }

                this.postClients(this.client)
            } else{
                Swal.fire({
                    icon: 'error',
                    text: 'Complete todos los campos',
                })
            }
        },
        deleteClient(id){
            let idClient = id._links.self.href
            axios.delete(idClient)
            .then(() => this.cargarDatos())
            
        },

        postClients(cliente){
            axios.post("/rest/clients", cliente)
            .then(() => this.cargarDatos()
            )  
            
        },
        editCliente(client) {
            this.idCliente = client._links.self.href
            this.inputFirstName = client.firstName
            this.inputLastName = client.lastName
            this.inputemail = client.email
        },

        confirmarModificacion(){
            axios.put(this.idCliente , 
                
                newClient = {
                    firstName :this.inputFirstName,
                    lastName :this.inputLastName,
                    email : this.inputemail
                })
            .then(() => this.cargarDatos())
        },

        newLoan() {
            if(this.maxAmount == undefined || this.nameLoan == '' || this.arrPayments.length == 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Something went wrong!',
                })
            } else {
                const paymentsInt = this.arrPayments.map(prop => parseInt(prop))
                axios.post('/api/loans/admin', `name=${this.nameLoan}&maxAmount=${this.maxAmount}&payments=${paymentsInt}&loanPercentaje=${this.percentaje}`)
                    .then(() => {
                        Swal.fire(
                            'Loan Created!',
                            'Loan available for everyone!',
                            'success'
                        )
                    })
                    .catch(error => console.error(error))
    }
},
    },
})
app.mount("#app")
