<h1>Permanent Creation</h1>
<a type="button" href="/">Home View</a>
<h1>Form</h1>
<script>
    import { employee } from "./form.js";
    import axios from 'axios'; 

    let ChildrenLength = 0;
    let testing = null;
    function PostData(node) {
        console.log($employee);
        removeChildrenNull($employee);
        let postArray =[];
        postArray.push($employee);
        const requestAPI = async () => {
          
            axios
                .post("http://localhost:8080/Permanent", postArray)
                .then((response) => testing= response.data)
                .catch((err) => console.log(err));
                    };
      
        requestAPI();
    }
    function pushChildren() {
      $employee.Children = [];
      for(let i=0;i<ChildrenLength+1; i++) {
        $employee.Children.push({
          age:null
        })
        console.log($employee.Children  )
      }
      
    }
    function removeChildrenNull(element) {
      element.Children.forEach((element,index) => {
        if(element.age == null) {
          $employee.Children.splice(1,index);
        }
        
      });
    }
   
</script>
<form on:submit|preventDefault={PostData} class="content">
  <label for="firstName">FirstName</label>
  <input type="text" id ="firstName" bind:value={$employee.firstName} />
  <label>last Name</label>
  <input type="text" bind:value={$employee.lastName} />
  <label>Children</label>
  <input type="number" on:change={pushChildren} bind:value={ChildrenLength} />
  {#if ChildrenLength >0 }
       {#each Array(ChildrenLength) as _, index (index) }
       <label>Children {index} Age</label>
        <input type="number" bind:value={$employee.Children[index].age} />
        
       {/each}
    {/if}
  <label>Phone</label>
  <input type="text" bind:value={$employee.phone} />
  <label>IBAN</label>
  <input type="text" bind:value={$employee.IBAN} />
  <label>Bank Name</label>
  <input type="text" bind:value={$employee.bankName} />
  <label>isMarried</label>
  <div>
    <label for="">True</label>
  <input type="radio" bind:group={$employee.isMarried}  value={true}/>
  <label for="">False</label>
  <input type="radio" bind:group={$employee.isMarried}  value={false}/>
  </div>
  <label>isInactive</label>
  <div>
    <label for="">True</label>
  <input type="radio" bind:group={$employee.isinactive}  value={true}/>
  <label for="">False</label>
  <input type="radio" bind:group={$employee.isinactive}  value={false}/>
  </div>
  <label>Admin Place</label>
  <div>
    <label for="">True</label>
  <input type="radio" bind:group={$employee.isAdministritative}  value={true}/>
  <label for="">False</label>
  <input type="radio" bind:group={$employee.isAdministritative}  value={false}/>
  </div>
  

  <button type="submit"> Post</button>
</form>
<p>
	{JSON.stringify($employee)}

</p>
<div class="response">
    {#if testing != null}
        <p>{JSON.stringify(testing)}</p>
    {:else}
        <p>just right!</p>
    {/if}
  
</div>
<style>
    .content{
        display: grid;
        grid-template-columns: repeat(2,1fr);
    }
</style>