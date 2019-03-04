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
Sortable.create(devnull, {//Removal List
    group: "shared",

    onAdd: function (evt) {
        this.el.removeChild(evt.item);
    }
});