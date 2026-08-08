/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener('DOMContentLoaded', function() {

    // Find the elements we need to work with
    const addNewButton = document.querySelector('.add-new-button');
    const modal = document.getElementById('addCustomerModal');
    const closeModalButton = document.getElementById('closeModalButton');

    // Show the modal when the "+ Add New" button is clicked
    addNewButton.addEventListener('click', function() {
        modal.classList.add('show');
    });

    // Hide the modal when the close button (X) is clicked
    closeModalButton.addEventListener('click', function() {
        modal.classList.remove('show');
    });

});

