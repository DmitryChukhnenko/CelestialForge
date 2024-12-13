
document.onload((evt) => {
    button = document.getElementsByClassName('.delete-button')[0]
    button.onClick((e) => {
        if(!confirm('Вы уверены, что хотите удалить этот элемент?')) {
            e.preventDefault();
        }
    })
})