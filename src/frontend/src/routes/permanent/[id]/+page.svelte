<script>
    import { page } from '$app/stores';
    import EditForm from '../../EditForm.svelte';
    import axios from 'axios';
    import EditEmployeeClassForm from '../../EditEmployeeClassForm.svelte';
    import ChildrenView from '../../ChildrenView.svelte';
    let contractShow = false;
    let infoShow = false;
    let editShow = false;
    let ChildrenLength = 0;
    let showChildren = false;
    var items = [];
    
    async function load(){


        const id = window.location.href.replace("http://127.0.0.1:5173/permanent/","");
        const res =  await fetch(`http://localhost:8080/Permanent?id=${id}`);
        let item = null
        if(res.ok){
            let item = await res.json()
            items.push(item);
            return item;
          

        }
     
    }
    function toggleInfo() {
         infoShow = !infoShow;
        contractShow = false;
        editShow = false;
        showChildren = false;

    }
    function toggleEdit() {
        editShow = !editShow
         infoShow = false;
        contractShow = false;
        showChildren = false;


    }
    function toggleChildren() {
        editShow = false;
         infoShow = false;
        contractShow = false;
        showChildren = !showChildren;


    }
    function PostData(e) {
        console.log(e.detail);
        axios
            .post("http://localhost:8080/EmployeeUpdate",e.detail)
            .then(function(response) {
                console.log(response);
                window.location.href = "./"+e.detail.EID
            })
    }
    const PostClassData = (e) =>{
        console.log(e.detail)
        axios
            .post("http://localhost:8080/UpdateClass",e.detail)
            .then(function(response) {
                console.log(response);
                window.location.href = "./"+e.detail.EID
            })
    }

  
    
</script>
<a type="button" href="/">Home View</a>

<div >
    
    {#await load()}
  <p>loading</p>
    {:then items}
        {#each items as item }
            <h2 class="EID">Employee ID: {item.EID} </h2>
            <div class="employee-view">
                <li>First Name: {item.firstName}</li>
                <li>Last Name: {item.lastName}</li>
                <li>Address: {item.address}</li>
                <li>Telephone: {item.phone}</li>
                <li>IBAN {item.IBAN}</li>
                <li>isInactive: {item.isInactive}</li>
                <li>Bank Name: {item.bankName}</li>
                <li>Department: {item.department}</li>
            </div>
            
            <div class="salary">
                <li>
                    Contract Salary: {item.SalaryContract}
                </li>
                <li>
                    Final Salary: {item.finalSummary}
                </li>
                    
            </div>
            <button on:click={toggleEdit}>Edit Employee Class</button>
            <button>Fire Employee </button>
            <button on:click={toggleChildren}>Edit Children </button>
            <button on:click={toggleInfo} >Edit Employee </button>
            {#if contractShow}
                <h3>Contract of {item.lastName}</h3>
                <li>Start Date: {item.startDate}</li>
                <li>Administration: {item.permanent}</li>
                <li>Permanent: {item.administration}</li>
                
            {/if}
            {#if infoShow }
            <!-- svelte-ignore component-name-lowercase -->
             <EditForm on:editEmployee={PostData} 
                 firstName={item.firstName}
                 lastName={item.lastName}
                 phone={item.phone}
                 IBAN={item.IBAN}
                 bankName={item.bankName}
                 isMarried={item.isMarried}
                 address={item.address}
                 isinactive={item.isInactive}
                 eid={item.EID}
                 department={item.department}

             >
     
             </EditForm>
            {/if} 
            {#if editShow }

            <EditEmployeeClassForm 
                on:editClass={PostClassData}
                eid={item.EID}
                permanent={item.permanent}
                administration={item.isAdministration}
                startDate={item.startDate}
            
            />
            {/if}
            {#if showChildren}
                <ChildrenView
                    eid={item.EID}
                >

                </ChildrenView>
            {/if} 
        {/each}
        
     {/await}
    
     
       
</div>


<style>
    .salary{
        border: 1px solid black;
        width: 100%;
        text-align: center;
    }
    .employee-view{
        display: grid;
        grid-template-columns: repeat(2,1fr);
        text-align: center;
    }
    .EID{
        color: rebeccapurple;
        text-align: center;
    }
    
</style>