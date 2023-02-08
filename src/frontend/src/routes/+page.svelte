<script>
import ExtraActions from "./ExtraActions.svelte";
async function fetchData() {
    const res = await fetch("http://localhost:8080/Permanent");
    const data = await res.json();

    if (res.ok) {
      return data;
    } else {
      throw new Error(data);
    }
}
async function fetchTemporary() {
    const res = await fetch("http://localhost:8080/Temporary");
    const data = await res.json();

    if (res.ok) {
      return data;
    } else {
      throw new Error(data);
    }
}
async function createDummy() {
    alert("Dummy data has been Created");
    const res = await fetch("http://localhost:8080/createDummy");
    const data = await res.json();

    if (res.ok) {
        return data;
    } else {
        throw new Error(data);
    }
}
async function createTables() {
    alert("Tables has been Created");
    const res = await fetch("http://localhost:8080/CreateTables");
    const data = await res.json();

    if (res.ok) {
        return data;
    } else {
        throw new Error(data);
    }
}
</script>
<a type="button" href="/addTemporary">Add Temporary</a>
<a type="button" href="/addPermanent">Add Permanent</a>
<button on:click={createDummy}> Create Dummy </button>
<button on:click={createTables}> Create Tables </button>
<h1>
    Employeess:

</h1>
<div class="central-view">
    


    <h3>Temporary</h3>
    <ul  id="temporary-view">
        
        <ul>
            {#await  fetchTemporary()}
        <!-- promise is pending -->
            <p>waiting for the promise to resolve...</p>
            {:then value}
                {#each value as item }
                    <li>
                        {item.firstName} {item.lastName} 
                        <a type="button" href={`/temporary/${item.EID}`}>
                            <button>
                                edit
                            </button>
                          
                        </a> 
                    
                    </li>
                {/each}
            <!-- promise was fulfilled -->
            <!-- {} -->
            {:catch error}
            <!-- promise was rejected -->
            <p>Something went wrong: {error.message}</p>
            {/await}
        </ul>
        
    </ul>

    <h2>Permanent</h2>
    <ul  id="testing">
        
        <ul>
            {#await  fetchData()}
        <!-- promise is pending -->
            <p>waiting for the promise to resolve...</p>
            {:then value}
                {#each value as item }
                    <li>{item.firstName} {item.lastName}
                        <a type="button" href={`/permanent/${item.EID}`}>
                            <button>
                                Edit
                            </button>
                        </a> 
                    </li>
                {/each}
            <!-- promise was fulfilled -->
            <!-- {} -->
            {:catch error}
            <!-- promise was rejected -->
            <p>Something went wrong: {error.message}</p>
            {/await}
        </ul>
        
    </ul>
</div>
<ExtraActions></ExtraActions>




<style>
   
    .central-view{
        display: grid;
        grid-template-columns: repeat(4,25rem);
    }
    li button{
        margin-left: 4px;
    }
    button{
        width: 5rem;
    }
</style>