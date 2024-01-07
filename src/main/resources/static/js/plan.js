// 載入
function init() {
    $.ajax({
        url: "/plan/all", // 資料請求的網址
        type: "GET", // GET | POST | PUT | DELETE | PATCH
        // data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
        dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
        success: function (data) {
            // console.log(data);
            let list_html = "";
            $.each(data, function (index, item) {
                list_html += `
                <tr data-planid="${item.planID}" data-planname="${item.planName}">
                    <td>${item.planID}</td>
                    <td>${item.planName}</td>
                    <td>${item.liter}</td>
                    <td>${item.planPrice}</td>
                    <td>${item.planPricePerCase}</td>
                    <td>${item.times}</td>
					<td><a href="update?planID=${item.planID}"><span class="sl_btn_chakan" style="background-color: #9ac972">修改</span></a></td>
                    <td><input id="del" class="sl_btn_chakan" type="submit" value="刪除"></td>
                </tr>
            `;
            });
            $(".plan_list").html(list_html);
            $('#example4').DataTable();
        }
    });
}

// enter送出
$("input#planName, input#liter, input#planPrice, input#planPricePerCase").on("keyup", function(e) {
	if (e.which == 13) {
		//enter也等於按按鍵
		$("button#task_add, button#task_update").click();
	}
});

// ====新增====
$("button#task_add").on("click", function() {
	// console.log($("input#planName"));
	let planName = $("input#planName").val().trim();
	let liter = $("input#liter").val().trim();
	let planPrice = $("input#planPrice").val().trim();
	let planPricePerCase = $("input#planPricePerCase").val().trim();
	let planUpload = $("input#planUpload").val();

	if (planName !== "" && liter !== "" && planPrice !== "" && planPricePerCase !== "") {

		if (!$(this).hasClass("-disabled")) {
			let form_data = {
				"planName": planName,
				"liter": liter,
				"planPrice": planPrice,
				"planPricePerCase": planPricePerCase,
				"planUpload": planUpload
			};

			$.ajax({
				url: "/plan/adding", // 資料請求的網址
				type: "POST", // GET | POST | PUT | DELETE | PATCH
				// data: form_data, // 將物件資料(不用雙引號) 傳送到指定的 url
				contentType: "application/json",
				data: JSON.stringify(form_data),
				dataType: "text", // 預期會接收到回傳資料的格式： json | xml | html
				success: function(item) {
					alert(item);
					if(item == '新增成功')
						location.reload();
				},
				error: function(xhr) {         // request 發生錯誤的話執行
					if (xhr.status === 400) {
						var error = JSON.parse(xhr.responseText);
						alert(error.message);
					} else {
						alert('連線異常');
					}
				}
			});
		}
	} else {
		alert("請填寫所有欄位");
	}
});

// ====刪除====
$(document).on("click", "input#del", function() {
	let r = confirm("是否確認刪除？");
	if (r) {
		let planName = $(this).closest('tr').data('planname');
		let that = this;
		$.ajax({
			url: "/plan/deleting",           // 資料請求的網址
			type: "DELETE",
			contentType: "application/json", // Set the content type to JSON
			data: JSON.stringify({ "planName": planName }), // Convert the data to JSON                  // GET | POST | PUT | DELETE | PATCH
			dataType: "text",             // 預期會接收到回傳資料的格式： json | xml | html
			beforeSend: function() {       // 在 request 發送之前執行
			},
			success: function(data) {
				alert(data);
				if(data == '刪除成功') {
					$(that).closest('tr').animate({
						"opacity": 0
					}, 1000, "swing", function() {
						$(this).remove();
					});
				}
			},
			error: function(xhr) {         // request 發生錯誤的話執行
				var errorMessage = xhr.responseText;
				 if (xhr.status === 500) {
					alert('連線異常');
				 } else {
					alert(errorMessage);
				 }
			},
			complete: function() {
			}
		});
	}
});

// ====更新====
$("button#task_update").on("click", function() {
	let planName = $("input#planName").val().trim();
	let liter = $("input#liter").val().trim();
	let planPrice = $("input#planPrice").val().trim();
	let planPricePerCase = $("input#planPricePerCase").val().trim();
	let times = $("#times").val();
	let planUpload = $("input#planUpload").val();
	if (planName !== "" && liter !== "" && planPrice !== "" && planPricePerCase !== "") {

		let update_data = {
			"planID": $("input#planID").val(),
			"planName": planName,
			"liter": liter,
			"planPrice": planPrice,
			"planPricePerCase": planPricePerCase,
			"times": times,
			"planUpload": planUpload
		}
		$.ajax({
			url: "/plan/updating",           // 資料請求的網址
			type: "PUT",                  // GET | POST | PUT | DELETE | PATCH
			// data: { "wayID": wayID, "wayName": wayName },                // 將物件資料(不用雙引號) 傳送到指定的 url
			contentType: "application/json",
			data: JSON.stringify(update_data),
			dataType: "text",             // 預期會接收到回傳資料的格式： json | xml | html
			success: function(item) {//第一層子元素為li標籤
				alert(item);
				if (item == '更新成功')
				window.location.href = '/plan/';
			},
			error: function(xhr) {         // request 發生錯誤的話執行
			var error = JSON.parse(xhr.responseText);
				if (xhr.status === 400) {
					alert(error.message);
				} else if (xhr.status === 500) {
					alert('連線異常');
				} else if (xhr.status === 200) {
					alert('價格不可低於案件報酬');
				}
			}
		});
	} else {
		alert('請填寫所有欄位');
	}
});
