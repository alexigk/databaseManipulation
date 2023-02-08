<script>
    import { createEventDispatcher } from "svelte";
    let dispatch = createEventDispatcher();
    export let eid;
    export let permanent;
    export let administration;
    export let startDate;

    const PostData = () => {
        const item = {
            EID:eid,
            permanent:permanent,
            administration:administration,
            startDate:startDate
        }
        dispatch('editClass',item);
    }
</script>
<form on:submit|preventDefault={PostData} class="content">
    <label for="">Permanent: </label>
    <div>
       
        <label for="true">True</label>
        <input type="radio" id="true" bind:group={permanent}  value={true}/>
        <label for="false">False</label>
        <input type="radio"id="false"  bind:group={permanent}  value={false}/>

    </div>
    <label for="">is Administration: </label>
    <div>
        <label for="true">True</label>
        <input type="radio" id="true" bind:group={administration}  value={true}/>
        <label for="false">False</label>
        <input type="radio"id="false"  bind:group={administration}  value={false}/>
    </div>
    <label for="startDate">Started From: </label>
    <input type="date" id="startDate" bind:value={startDate} />

    <button type="submit" >Apply Changes</button>
</form>

<style>
    .content{
        display: grid;
        grid-template-columns: repeat(2,1fr);
    }
</style>