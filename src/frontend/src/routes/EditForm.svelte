<script>
    import { createEventDispatcher } from "svelte";
    export let firstName;
    export let lastName;
    export let phone;
    export let IBAN;
    export let  bankName;
    export let isMarried;
    export let address;
    export let isinactive;
    export let eid;
    export let department;
    let dispatch = createEventDispatcher();
    const PostData = () => {
        const item = {
             firstName: firstName,
             lastName: lastName,
             phone: phone,
             IBAN: IBAN,
             bankName: bankName,
             isMarried: isMarried,
             address: address,
             isinactive: isinactive,
             EID:eid,
             department:department
        }
        dispatch('editEmployee',item);
    }
</script>





<form on:submit|preventDefault={PostData} class="content">


    <label for="firstName">FirstName</label>
    <input type="text" id ="firstName" bind:value={firstName} />
    <label>last Name</label>
    <input type="text" bind:value={lastName} />
    <label>Phone</label>
    <input type="text" bind:value={phone}/>
    <label>IBAN</label>
    <input type="text" bind:value={IBAN}>
    <label>Bank Name</label>
    <input type="text" bind:value={bankName} />
    <label>isMarried</label>
    <div>
    <label for="">True</label>
    <input type="radio" bind:group={isMarried}  value={true}/>
    <label for="">False</label>
    <input type="radio" bind:group={isMarried}  value={false}/>
    </div>
    <label>isInactive</label>
    <div>
    <label for="">True</label>
    <input type="radio" bind:group={isinactive}  value={true}/>
    <label for="">False</label>
    <input type="radio" bind:group={isinactive}  value={false}/>
    </div>
    <label>Address: </label>
    <input type="text" bind:value={address} />
    <label>Department: </label>
    <input type="text" bind:value={department} />
    <button type="submit" >Apply Changes</button>
</form>

<style>
    .content{
        display: grid;
        grid-template-columns: repeat(2,1fr);
    }
</style>