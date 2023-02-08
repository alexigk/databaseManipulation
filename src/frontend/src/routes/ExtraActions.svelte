<script>
    import axios from 'axios';
    let showSalaryCategories = false;
    let categories = ["Permanent", "Temporary","Administration","Instructional", "All"];
    let selectedCategory = null;
    let varshowMinMax = false;
    function showCategories() {
        showSalaryCategories = !showSalaryCategories;
        selectedCategory = null;
    }
    function showMinMax() {
        varshowMinMax = !varshowMinMax
        showSalaryCategories = false;
    }
    function getReportForAll() {
        selectedCategory = null;

        selectedCategory = categories[4];
    }
    function getReportForPermanent() {
        selectedCategory = null;

        selectedCategory = categories[0];

    }
    function getReportForTemporary() {
        selectedCategory = null;

        selectedCategory = categories[1];

    }
    function getReportForAdministration() {
        selectedCategory = null;

        selectedCategory = categories[2];

    }
    function getReportForInstructional() {
        selectedCategory = null;

        selectedCategory = categories[3];

    }

    async function fetchAll() {
        let item = null

        const category = {
            "Category": selectedCategory
        }
     
      const res =  await fetch(`http://localhost:8080/SalaryReport`,{
            method: "POST",
             headers: {
            'Content-Type': 'application/json',
             },
            body: JSON.stringify(category),
        })
        if(res.ok){
            let item = await res.json()
            return item;
          

        }
    }
</script>



<div class="extra-buttons">
    <h2>Extra Actions</h2>
    <div class="button-actions">
        <button on:click={showCategories}>Get Salary Report</button>
       <button on:click={showMinMax}>Min/Max/Avg Salary</button>
    </div>
    <div class="category-button">
        {#if showSalaryCategories}
            <button on:click={getReportForAll}>Salary Report for All</button>
            <button on:click={getReportForPermanent}>Salary Report for Permanent</button>
            <button on:click={getReportForTemporary}>Salary Report for Temporary</button>
            <button on:click={getReportForAdministration}>Salary Report for Administration</button>
            <button on:click={getReportForInstructional}>Salary Report for Instructional</button>
        {/if }
        
    </div>
    <div>
        {#if selectedCategory == categories[0]}
            <h4>Permanent Staff Salary</h4>
            {#await fetchAll()}
                    <p>loading</p>
                {:then salaries } 
                        {#each salaries as  salary}
                            <p>{salary.firstName} {salary.lastName}  {salary.finalSummary}</p>
                        {/each}
            {/await}
        {:else if selectedCategory == categories[1]}
            <h4>Temporary Staff Salary</h4>
            {#await fetchAll()}
                    <p>loading</p>
                {:then salaries } 
                        {#each salaries as  salary}
                            <p>{salary.firstName} {salary.lastName}  {salary.finalSummary}</p>
                        {/each}
            {/await}
        {:else if selectedCategory == categories[2]}
             <h4>Administration Staff Salary</h4>
             {#await fetchAll()}
             <p>loading</p>
            {:then salaries } 
                        {#each salaries as  salary}
                            <p>{salary.firstName} {salary.lastName}  {salary.finalSummary}</p>
                        {/each}
            {/await}
        {:else if selectedCategory == categories[3]}
             <h4>Instructional Staff Salary</h4>
             {#await fetchAll()}
                    <p>loading</p>
                {:then salaries } 
                        {#each salaries as  salary}
                            <p>{salary.firstName} {salary.lastName}  {salary.finalSummary}</p>
                        {/each}
            {/await}
        {:else if selectedCategory == categories[4]}
            <h4>Report Salary</h4>
            {#await fetchAll()}
                    <p>loading</p>
                {:then salaries } 
                        {#each salaries as  salary}
                            <p>{salary.firstName} {salary.lastName}  {salary.finalSummary}</p>
                        {/each}
            {/await}
            
        {/if}
  
    </div>
    <div class="minmax">
        {#if varshowMinMax}
         <h4>Min/Max/Avg Salaries</h4>
            
        {/if}
    </div>
</div>

   
<style>
    .extra-buttons{
        border-top: 1px solid purple;
        margin-top: 1rem;
        text-align: center;
    }
</style>
