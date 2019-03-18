// Editor list (left)
Sortable.create(editorList, {
    group: 'shared',
    animation: 150
});

// Instruction list (right)
Sortable.create(instructionList, {
    group: {
        name: 'shared',
        pull: 'clone',
        put: false // Do not allow items to be put into this list
    },
    animation: 150,
    sort: false // To disable sorting: set sort to false
});

//Removal List
Sortable.create(devnull, {
    group: "shared",

    onAdd: function (evt) {
        this.el.removeChild(evt.item);
    }
});
