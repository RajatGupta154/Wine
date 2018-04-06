$(document).ready(function(){
        $('.facets input').on("change",function () {

            var page = $('.facets').attr("data-page");
            var sort = $('.facets').attr("data-sort");
            // var checkboxes = document.querySelectorAll('input[type=checkbox]:checked')
            // if(checkboxes.size == 3){
            //     category = checkboxes[0].val;
            //     year = checkboxes[1].val;
            //     type = checkboxes[2].val;
            //     window.location.href="/wine-1/shop/"+page+"?argSort="+sort+"category="+category+"year="+year+"type="+type;
            // }
            // else if(checkboxes.size == 2) {
            //     category = checkboxes[0].val;
            //     year = checkboxes[1].val;
            //     window.location.href="/wine-1/shop/"+page+"?argSort="+sort+"category="+category+"year="+year;
            // }
            // else if(checkboxes.size == 1){
            //     category = checkboxes[0].val;
            //     window.location.href="/wine-1/shop/"+page+"?argSort="+sort+"category="+category;
            // }
            // else{
            //     window.location.href="/wine-1/shop/"+page+"?argSort="+sort;
            // }

            var category = document.querySelector('.category').checked;
            var type = document.querySelector('.type').checked;
            var year = document.querySelector('.year').checked;
            window.location.href="/wine-1/shop/"+page+"?argSort="+sort+"category="+category+"year="+year+"type="+type;


        //     var categories = $('.facets').attr("category");
        //     var year = $('.facets').attr("year");
        //     var type = $('.facets').attr("type");
        //     $('input:checked').forEach(function() {
        //        $(this).attr("name");
        //       $(this).val();
        //      alert($(this).val());
        //     }
        //     alert($( "input:checked" ).val());
        // })
    }
)