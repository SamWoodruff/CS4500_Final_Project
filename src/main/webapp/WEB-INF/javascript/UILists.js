// Editor list
Sortable.create(editorList, {
    group: 'shared',
    animation: 150
});
// Instruction list


Sortable.create(instructionList, {
    group: {
        name: 'shared',
        pull: 'clone',
        put: false // Do not allow items to be put into this list
    },
    animation: 150,
    sort: false // To disable sorting: set sort to false
});
// Removal list
Sortable.create(removalList, {
    group: {
        put:true
    },
});