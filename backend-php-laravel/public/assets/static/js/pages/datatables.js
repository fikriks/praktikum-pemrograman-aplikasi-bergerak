let jquery_datatable = $("#table").DataTable({
    responsive: true,
});

const setTableColor = () => {
    document
        .querySelectorAll(".dataTables_paginate .pagination")
        .forEach((dt) => {
            dt.classList.add("pagination-primary");
        });
};
setTableColor();
jquery_datatable.on("draw", setTableColor);
