<script>
    let childAge
    export let eid;
    import axios from 'axios';
    const addChild = () => {
        const newChild = {
            EID: parseInt(window.location.href.replace("http://127.0.0.1:5173/permanent/","")),
            age:childAge
        }
        console.log(newChild);
        axios
            .post("http://localhost:8080/GetChildren",newChild)
            .then(function(response) {
                console.log(response);
                // window.location.href = "./"+newChild.EID
            })
    }
    async function fetchChildren()  {
        const res =  await fetch(`http://localhost:8080/GetChildren?id=${eid}`);
        let item = null
        if(res.ok){
            let item = await res.json()
            return item;
          

        }
    }
</script>
{#await fetchChildren()}
<p>loading</p>
{:then children} 
    <div class="children">
        {#each children as child }
        <label for="CID">CID:</label>
        <input type="number" name="CID" bind:value={child.CID}>
        <label for="age">Age:</label>
        <input type="number" name="age" bind:value={child.childAge}>
        

            
        {/each}
    </div>
    

{/await}

<h4 style="border-top:1px solid black; margin-top:1rem;">New Child</h4>
<label for="newAge">Age:</label>
<input type="number" name="newAge" bind:value={childAge}>
<button on:click={addChild}>Add Child</button>

<style>
    .children{
        text-align: center;
        display: grid;
        grid-template-columns: repeat(4,5rem);
    }
    label + input {
        margin-left: 3px;
    }
</style>