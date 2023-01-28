const {createApp} = Vue;

const app = createApp({
    data(){
        return{
            json :[],
            transactions:[],
            accounts:[],
            queryString:undefined,
            balance:undefined,
            startDate:"",
            endDate:"",
            accountNumber:"",
            

        }
    },
    created (){
        this.cargarDatos()
        
    },
    methods:{
        
        cargarDatos(){

            this.queryString = location.search;
            let params = new URLSearchParams(this.queryString);
            let id = params.get("id")
            axios.get("/api/clients/current")
            .then(response => {
            this.json = response.data;
            this.accounts = this.json.account.find(account => account.id == id).number
            this.balance = this.json.account.find(account => account.id == id).balance
            this.transactions = this.json.account.find(account => account.id == id).transaction;
            
            this.transactions.sort((a,b) => {
                if(a.id > b.id){
                    return -1
                }
            })
        })
            .catch(error => console.log(error));
    },

        generatePDF(){
            axios({
                url: "/api/transactions/pdf",
                method: "POST",
                data: {

                    account:this.accounts,
                    dateStart:this.startDate,
                    dateEnd:this.endDate,
                    
                },
                responseType: "blob"
            })
            .then(response =>{
                const href = URL.createObjectURL(response.data)
                const link = document.createElement("a")
                link.href = href
                link.setAttribute("download", "transactions.pdf")
                document.body.appendChild(link)
                link.click()
            })
            .catch()
        },

    salir(){
        axios.post('/api/logout')
        .then(() => location.href = "/web/index.html")
    }
},
})
app.mount("#app")
