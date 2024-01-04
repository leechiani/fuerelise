let openModal = false

function init() {
    // if (openModal == false) {
    //     getApi();
    // }

    function getApi() {
        $.ajax({
            url: `${API_BASE_URL}/mem-estab-case`,           // 資料請求的網址
            type: "GET",                  // GET | POST | PUT | DELETE | PATCH
            xhrFields: {
                withCredentials: true
            },
            // data:,            // 將物件資料(不用雙引號) 傳送到指定的 url
            dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
            beforeSend: function () {       // 在 request 發送之前執行
            },
            success: function (data) {      // request 成功取得回應後執行

                let list_html_1 = '';
                let list_html_2 = '';
                let list_html_3 = '';
                let list_html_5 = '';
                let html_6 = `
                <div class="modal fade " id="exampleModal-dd" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content ">
                            <div class="modal-header mb_modal_header">
                                <div class="mb_modal_header_h5">
                                    <h5 class="modal-title mb_modal_title" id="planOrdID" planOrdID="${data.planOrdID}" style="font-weight: 900;">訂單標號 : ${data.planOrdID}</h5>
                                </div>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body mb_modal_body">
                                <div id="planForm" dayCode="${toIntday(data.day)}">
                                    <div class="mb-3 mb_modal_body_div " >
                                        
                                        <p class="mb_plan_name" style="font-weight: 600; font-size:1rem; margin:0px;">方案名稱 : ${data.planName}</p>
                                        <p class="mb_plan_time_range" style="font-weight: 600;">收取時間 : ${data.timeRange}</p>
                                        <div class="mb_plan_ord_price">
                                            <div class="mb_plan_monthly">
                                                <p class="mb_plan_period" style="font-weight: 600;">訂閱時長 : ${data.planPeriod} 個月<samp>，</samp></p>
                                                <p class="mb_plan_week_cont" style="font-weight: 600;">每週${data.day}次</p>
                                            </div>
                                           
                                        </div>
                                        <div class="mb_plan_weekly" data-dayCode="${data.dayCode}" style="font-weight: 600;">
                                            <p class="mb_mon mb_off">星期一</p>
                                            <p class="mb_tue mb_off">星期二</p>
                                            <p class="mb_wed mb_off">星期三</p>
                                            <p class="mb_thu mb_off">星期四</p>
                                            <p class="mb_fri mb_off">星期五</p>
                                            <p class="mb_sat mb_off">星期六</p>
                                            <p class="mb_sun mb_off">星期日</p>
                                        </div>
                                        
                                            <p class="mb_plan_way" style="font-weight: 600;"><span style="font-weight: 600;">收取方式 : ${data.wayName}</p>
                                        <p style="color:red;">注意:每筆訂單僅允許進行一次修改。</p>
                                        <p style="color:red;">完成修改後，系統僅調整當前時間之後三天或更長的訂單。當前時間三天內的訂單日期將保持不變。</p>
                                          
                                    </div>
                                    <div class="mb_estab_case">
                                    <p style="margin-bottom: 10px;">收取時間</p>
                                    <div class="mb_plan_time_range_btn">
                                        <button tyep="button" class="btn btn-outline-success mb_plan_time_ranges mb_plan_time_range_09_btn" rangeID="240001">09:00 ~ 17:00</button>
                                        <button tyep="button" class="btn btn-outline-success mb_plan_time_ranges mb_plan_time_range_19_btn" rangeID="240002">19:00 ~ 21:00</button>
                                        <button tyep="button" class="btn btn-outline-success mb_plan_time_ranges mb_plan_time_range_21_btn" rangeID="240003">21:00 ~ 22:30</button>
                                    </div>
                                    <p style="margin-bottom: 10px;">請選擇每週收取日 (請選擇${data.day}日)</p>
                                    <div class="mb_plan_weekly_btn" dayCode="${toIntday(data.day)}">
                                        <button tyep="button" class="btn btn-outline-success mb_week_btn mb_mon_btn" value="0">星期一</button>
                                        <button tyep="button" class="btn btn-outline-success mb_week_btn mb_tue_btn" value="0">星期二</button>
                                        <button tyep="button" class="btn btn-outline-success mb_week_btn mb_thu_btn" value="0">星期四</button>
                                        <button tyep="button" class="btn btn-outline-success mb_week_btn mb_fri_btn" value="0">星期五</button>
                                        <button tyep="button" class="btn btn-outline-success mb_week_btn mb_sat_btn" value="0">星期六</button>
                                    </div>       
                                </div>
                                
                                </div>
                                <div class="modal-footer mb_estab_case_level_modal_footer">
                                    <button type="button" class="btn btn-secondary mb_estab_case_complaint_cancel_btn" data-bs-dismiss="modal">取消修改</button>
                                    <button type="button" class="btn btn-primary mb_estab_case_complaint_confirm_btn mb_estab_case_complaint_btn">確認修改</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                `;
                let list_html_4 = `
                    <tr><th scope="row">今日無收取服務</th><td></td><td class="mb_estab_case_status_frame"></td><td class="mb_mem_complaint"></td><td></td></tr>`;;
                list_html_1 += `
                    <p class="mb_plan_name" style="font-weight: 900;">${data.planName}</p>
                    <p class="mb_plan_time_range">${data.timeRange}</p>
                    <div class="mb_plan_ord_price">
                        <div class="mb_plan_monthly">
                            <p class="mb_plan_period">${data.planPeriod} 個月<samp>，</samp></p>
                            <p class="mb_plan_week_cont">每週${data.day}次</p>
                        </div>
                        <p class="mb_plan_ord_total"><span>$</span>${data.total}</p>
                    </div>
                    <div class="mb_plan_weekly" data-dayCode="${data.dayCode}">
                        <p class="mb_mon mb_off">星期一</p>
                        <p class="mb_tue mb_off">星期二</p>
                        <p class="mb_wed mb_off">星期三</p>
                        <p class="mb_thu mb_off">星期四</p>
                        <p class="mb_fri mb_off">星期五</p>
                        <p class="mb_sat mb_off">星期六</p>
                        <p class="mb_sun mb_off">星期日</p>
                    </div>
                    <p class="mb_plan_way">${data.wayName}</p>   
                `;

                list_html_2 += `
                
                    <div class="mb_mem_info_01">
                        <p class="mb_plan_id"><span>訂單編號 : </span>${data.planOrdID}</p>
                        <p class="mb_plan_start"><span>方案開始時間 : </span>${data.planStart}</p>
                    </div>
                    <div class="mb_mem_info_02">
                        <p class="mb_plan_contact"><span>聯絡人 : </span>${data.contact}</p>
                        <p class="mb_plan_end"><span>方案開始結束 : </span>${data.planEnd}</p>
                    </div>
                    <p class="mb_plan_contact_tel"><span>連絡電話 : </span>${data.contactTel}</p>
                    <div class="mb_mem_info_03">
                        <p class="mb_plan_floor"><span>收取地址 : </span><span
                                class="mb_plan_city"></span>${data.cityCode}${data.cityName}${data.floor}${data.pickupStop}</p>
                        <div class="my_plan_info_btn d-grid gap-2 d-md-block">
                            <button class="btn mb_plan_Update_btn mb_update_01 rounded-pill"
                                 data-bs-toggle="modal" data-bs-target="#exampleModal-dd"
                                data-bs-whatever="@mdo">修改方案</button>
                            <button class="btn mb_plan_Update_btn mb_update_02 rounded-pill"
                                type="button">取消方案</button>
                        </div>
                    </div>
                

                `;


                $.each(data.memEstabCaseBO, function (index, item) {
                    let newDateTime = (new Date().toLocaleDateString().replace(/\//g, '-') + " " + new Date().getHours().toString().padStart(2, '0') + ":" + new Date().getMinutes().toString().padStart(2, '0') + ":" + new Date().getSeconds().toString().padStart(2, '0'));
                    let estabCaseDate = item.estabCaseDate;
                    let StatusNumber = item.estabCaseStatus;
                    let estabCaseLevel = item.estabCaseLevel;
                    let estabCaseFeedback = getDefaultText(item.estabCaseFeedback);
                    let estabCaseFBTime = item.estabCaseFBTime;
                    // let estabCaseFeedbacktext=getDefaultText2(estabCaseFeedback,index)
                    if (estabCaseDate == new Date().toLocaleDateString().replace(/\//g, '-') && StatusNumber != 3) {

                        list_html_4 = `
                            <tr class="mb_tr${index}">
                                <th scope="row">${estabCaseDate}</th>
                                <td>${data.timeRange}</td>
                                <td class="mb_estab_case_status_frame">
                                    <div class="mb_estab_case_status mb_estab_case_status_1 mb_estab_case_status_2  mb_estab_case_status_3 mb_status${index}" id="estab-case-status${index}" estabCaseStatus="${StatusNumber}"></div>
                                </td>
                                <td class="mb_mem_complaint"><button
                                    class="btn mb_mem_complaint_btn rounded-pill"  data-bs-toggle="modal" data-bs-target="#exampleModal-c${index}"
                                    data-bs-whatever="@mdo">意見反映</button></td>
                                <td><button class="btn mb_estab_case_level_btn rounded-pill"  data-bs-toggle="modal" data-bs-target="#exampleModal-l${index}"
                                    data-bs-whatever="@mdo" >評價</button>
                                </td>
                            </tr>
                        `;
                    } else {

                    }

                    if (StatusNumber != 3) {

                        list_html_3 += `
                            <tr class="mb_tr${index}">
                                <th scope="row">${estabCaseDate}</th>
                                <td>${data.timeRange}</td>
                                <td class="mb_estab_case_status_frame">
                                    <div class="mb_estab_case_status mb_estab_case_status_1 mb_estab_case_status_2  mb_estab_case_status_3 mb_status${index}" id="estab-case-status${index}" estabCaseStatus="${StatusNumber}"></div>
                                </td>
                                <td class="mb_mem_complaint"><button
                                        class="btn mb_mem_complaint_btn rounded-pill"  data-bs-toggle="modal" data-bs-target="#exampleModal-c${index}"
                                        data-bs-whatever="@mdo">意見反映</button></td>
                                <td><button class="btn mb_estab_case_level_btn rounded-pill"  data-bs-toggle="modal" data-bs-target="#exampleModal-l${index}"
                                    data-bs-whatever="@mdo" >評價</button>
                                </td>
                            </tr>
                        `;
                        list_html_5 += `
                        <div class="modal fade " id="exampleModal-l${index}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" estabCaseFBTime="
                    ${getNewDateTime(estabCaseFBTime)}">
                        <div class="modal-dialog">
                            <div class="modal-content ">
                                <div class="modal-header mb_modal_header">
                                    <div class="mb_modal_header_h5">
                                        <h5 class="modal-title mb_modal_title" id="exampleModalLabel">訂單標號 : ${item.estabCaseID}</h5>
                                    </div>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body mb_modal_body">
                                    <form id="myForm">
                                        <div class="mb-3 mb_modal_body_div" >
                                            <div class="mb_modal_div">
                                                <!-- 方案及商品基本資料 -->
                                                <label  class="col-form-label mb_modal_label">收取日期 : ${estabCaseDate}</label>
                                                <label  class="col-form-label mb_modal_label">時段 : ${data.timeRange}</label>
                                                <label  class="col-form-label mb_modal_label">收取員編號 : ${item.empID}</label>
                                            </div>
                                                <!-- 會員名稱 -->
                                            <label  class="col-form-label mb_mem_name">${data.memName}</label>
                                            <br>
                                            <div class="star_block mb_star${index}">
                                                <span class="star mb_star ${item.estabCaseLevel >= 1 ? '-on' : ''}" data-star="1"><i class="fas fa-star " style="width: 30px; height: 30px;"></i></span>
                                                <span class="star mb_star ${item.estabCaseLevel >= 2 ? '-on' : ''}" data-star="2"><i class="fas fa-star " style="width: 30px; height: 30px;"></i></span>
                                                <span class="star mb_star ${item.estabCaseLevel >= 3 ? '-on' : ''}" data-star="3"><i class="fas fa-star " style="width: 30px; height: 30px;"></i></span>
                                                <span class="star mb_star ${item.estabCaseLevel >= 4 ? '-on' : ''}" data-star="4"><i class="fas fa-star " style="width: 30px; height: 30px;"></i></span>
                                                <span class="star mb_star ${item.estabCaseLevel >= 5 ? '-on' : ''}" data-star="5"><i class="fas fa-star " style="width: 30px; height: 30px;"></i></span>
                                            </div>
                                        </div>
                                        <div class="mb-3 ">
                                            <label for="message-text${index}" class="col-form-label">內容</label>
                                            <textarea class="form-control mb_level_feedback" id="message-text${index}" placeholder='說說你的建議' estabID="${item.estabCaseID}" estabCaseFeedback=${estabCaseFeedback} >${estabCaseFeedback}</textarea>
                                        </div>
                                        <div class="modal-footer mb_estab_case_level_modal_footer">
                                            <button type="button" class="btn btn-secondary mb_estab_case_level_cancel_btn" data-bs-dismiss="modal">取消</button>
                                            <button type="submit" class="btn btn-primary mb_estab_case_level_confirm_btn" id="level_confirm_btn${index}"  >送出</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="modal fade " id="exampleModal-c${index}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content ">
                                <div class="modal-header mb_modal_header">
                                    <div class="mb_modal_header_h5">
                                        <h5 class="modal-title mb_modal_title" id="exampleModalLabel">訂單標號 : ${item.estabCaseID} ${item.comStatus == true ? '(已處理完成)' : ''}</h5>
                                    </div>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body mb_modal_body">
                                    <form id="comForm">
                                        <div class="mb-3 mb_modal_body_div" >
                                            <div class="mb_modal_div">
                                                <!-- 方案及商品基本資料 -->
                                                <label  class="col-form-label mb_modal_label">收取日期 : ${estabCaseDate}</label>
                                                <label  class="col-form-label mb_modal_label">時段 : ${data.timeRange}</label>
                                                <label  class="col-form-label mb_modal_label">收取員編號 : ${item.empID}</label>
                                            </div>
                                        
                                            <div class="mb-3">
                                                <label for="recipient-name${index}" class="col-form-label">聯絡電話 :</label>
                                                <input type="text" class="form-control mb_comTel" id="recipient-name${index}" required="required" value="${item.comTel != null ? item.comTel : data.contactTel}" >
                                            </div>
                                            <label  class="col-form-label mb_modal_label">申訴時間 :${item.comStart != null ? readyDateTime(item.comStart) : newDateTime} </label>
                                        </div>
                                        <div class="mb-3 ">
                                            <label for="complaint-text${index}" class="col-form-label">申訴內容</label>
                                            <textarea class="form-control mb_complaint" id="complaint-text${index}"placeholder='說說你的建議' required="required" estabID="${item.estabCaseID}" comTelID="${item.comTel != null ? item.comTel : ''}" >${item.comDetail != null ? item.comDetail : ''}</textarea>
                                        </div>
                                        <div class="modal-footer mb_estab_case_level_modal_footer">
                                            <button type="button" class="btn btn-secondary mb_estab_case_complaint_cancel_btn" data-bs-dismiss="modal">取消</button>
                                            <button type="submit" class="btn btn-primary mb_estab_case_complaint_confirm_btn" id="complaint_confirm_btn${index}" >送出</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
            
                        `;
                    }


                });




                //回頭測試
                function estabCaseStatus() {
                    let StatusText = "";
                    // console.log(data.memEstabCaseBO);
                    for (let i = 0; i < data.memEstabCaseBO.length; i++) {
                        StatusText = $('.mb_status' + i).attr("estabCaseStatus");
                        // console.log($('.mb_estab_case_status').attr("estabCaseStatus"));
                        switch (StatusText) {
                            case '0':
                                $('.mb_status' + i).removeClass("mb_estab_case_status_2  mb_estab_case_status_3");
                                $('.mb_status' + i).text("待收取");
                                break;
                            case '1':
                                $('.mb_status' + i).removeClass("mb_estab_case_status_1  mb_estab_case_status_3");
                                $('.mb_status' + i).text("完成收取");
                                break;
                            case '2':
                                $('.mb_status' + i).removeClass("mb_estab_case_status_1  mb_estab_case_status_2");
                                $('.mb_status' + i).text("逾時");
                                break;
                            case '3':
                                $('.mb_status' + i).removeClass("mb_estab_case_status_1  mb_estab_case_status_2");
                                $('.mb_status' + i).text("已取消");
                                break;
                        }
                    }
                }
                function getNewDateTime(estabCaseFBTime) {
                    if (estabCaseFBTime == null) {
                        estabCaseFBTime = (new Date().toLocaleDateString().replace(/\//g, '-') + " " + new Date().getHours().toString().padStart(2, '0') + ":" + new Date().getMinutes().toString().padStart(2, '0') + ":" + new Date().getSeconds().toString().padStart(2, '0'))
                        return estabCaseFBTime;
                    } else {
                        return estabCaseFBTime;
                    }
                }
                function getDefaultText(srt) {
                    if (srt == null) {
                        return '';
                    } else {
                        return srt;
                    }
                }
                function getLevelDefaultText2() {
                    let star = "";
                    let feedbackID = "";
                    let levelID = "";
                    for (let i = 0; i < data.memEstabCaseBO.length; i++) {
                        star = "div.mb_star" + i;
                        feedbackID = "#message-text" + i;
                        levelID = "#level_confirm_btn" + i
                        if (!(data.memEstabCaseBO[i].estabCaseFeedback == null)) {
                            $(feedbackID).attr("readonly", true);
                            $(star).addClass("mb_disabled");

                        }
                        if (!(data.memEstabCaseBO[i].estabCaseFeedback == null)) {
                            $(levelID).prop('disabled', true);

                        } else {
                            $(levelID).prop('disabled', false);
                        }
                    }

                }
                function getComDefaultText2() {

                    let comDetailID = "";
                    let comBtnID = "";
                    for (let i = 0; i < data.memEstabCaseBO.length; i++) {
                        comTelID = "#recipient-name" + i;
                        comDetailID = "#complaint-text" + i;
                        comBtnID = "#complaint_confirm_btn" + i
                        if (!(data.memEstabCaseBO[i].comDetail == null)) {
                            $(comDetailID).attr("readonly", true);
                            $(comTelID).attr("readonly", true);

                        }
                        if (!(data.memEstabCaseBO[i].comDetail == null)) {
                            $(comBtnID).prop('disabled', true);

                        } else {
                            $(comBtnID).prop('disabled', false);
                        }
                    }

                }

                function dayOfWeek() {
                    for (let i = 0; i < 7; i++) {

                        let dayCodeValue = $('.mb_plan_weekly').attr("data-dayCode").substring(i, i + 1);

                        if (dayCodeValue == 1) {

                            switch (i) {
                                case 0: $(".mb_mon").removeClass("mb_off"); break;
                                case 1: $(".mb_tue").removeClass("mb_off"); break;
                                case 2: $(".mb_wed").removeClass("mb_off"); break;
                                case 3: $(".mb_thu").removeClass("mb_off"); break;
                                case 4: $(".mb_fri").removeClass("mb_off"); break;
                                case 5: $(".mb_sat").removeClass("mb_off"); break;
                                case 6: $(".mb_sun").removeClass("mb_off"); break;
                            }
                        }
                    }
                }

                function removeCancelPlanBtn() {
                    let localDate = new Date();
                    let planStart = new Date(data.planStart);
                    if (planStart < localDate) {
                        // $(".mb_update_02").addClass("mb_hide");
                    }
                }

                $("div.mb_plan_info_body").html(list_html_1);
                
                $("tbody.mb_estab_case_all").html(list_html_3);
                $("tbody.mb_estab_case_today").html(list_html_4);
                $("div.mb_pop_up_windows").html(list_html_5);
                $("div.mb_mem_info").html(list_html_2);
                $("div#updatePlanOrdID").html(html_6);
                $("p.mb_mem_name").text(data.memName);
                dayOfWeek();
                estabCaseStatus();
                getLevelDefaultText2();
                getComDefaultText2();
                removeCancelPlanBtn();


                initElm = true
            },
            error: function (xhr) {         // request 發生錯誤的話執行
                console.log(xhr);
            },
            complete: function (xhr) {      // request 完成之後執行(在 success / error 事件之後執行)
                // console.log(xhr);

            }

        });
    };

    getApi();
    
}



$(function () {
    let activeButtonCountRange = 0;
    let activeButtonCountWeek = 0;

    init();
    const pollGetApi = () => {
        setTimeout(() => {
            closeModal();
            if (openModal == false) {
                init()
                activeButtonCountRange = 0;
                activeButtonCountWeek = 0;
                // getApi();
            }
          pollGetApi();
        }, 5000);
    }
    pollGetApi();

    $(document).on("click", "span.star", function () {
        let current_star = parseInt($(this).attr("data-star"));

        $(this).closest("div.star_block").find("span.star").each(function (i, e) {
            if (parseInt($(this).attr("data-star")) <= current_star) {
                $(this).addClass("-on");
            } else {
                $(this).removeClass("-on");
            }
        });

    });

    //限制使用者選取button數量(時間區間)
    let maxActiveButtonsRange = 1;
    $(document).on("click", ".mb_plan_time_ranges", function () {

        if ($(this).attr("rangeCode") == 'c01') {

            $(this).removeClass("mb_plan_time_btn_on");
            $(this).attr("rangeCode", " ")
            activeButtonCountRange--;

        } else {
            if (activeButtonCountRange < maxActiveButtonsRange) {
                $(this).addClass("mb_plan_time_btn_on");
                $(this).attr("rangeCode", 'c01')

                activeButtonCountRange++;


            }
        }

    });

    //限制使用者選取button數量(每周收取日)

    $(document).on("click", ".mb_week_btn", function () {

        if ($(this).attr("weekcode") == 'w01') {

            $(this).removeClass("mb_plan_weekly_btn_on");
            $(this).val(0);
            $(this).attr("weekcode", "")
            activeButtonCountWeek--;

        } else {
            if (activeButtonCountWeek < parseInt($("#planForm").attr('dayCode'))) {
                $(this).addClass("mb_plan_weekly_btn_on");
                $(this).val(1);
                $(this).attr("weekcode", "w01")

                activeButtonCountWeek++;


            }
        }

    });
    //送出評價(更新案件評價)
    $(document).on("submit", "#myForm", function (event) {

        // $("#myForm").submit(function (event) {
        // 防止表單默認提交行為
        event.preventDefault();
        // 獲取表單數據
        let setabID = $(this).find("textarea.mb_level_feedback").attr("estabID");
        let task_text = $(this).find("textarea.mb_level_feedback").val().trim();
        let current_star = parseInt($(this).find("span.-on").length);
        let form_data = {
            "estabCaseFeedback": task_text,
            "estabCaseID": parseInt(setabID),
            "estabCaseLevel": current_star
        };

        $.ajax({
            url: `${API_BASE_URL}/mem-estab-case`,           // 資料請求的網址
            type: "PATCH",                  // GET | POST | PUT | DELETE | PATCH
            xhrFields: {
                withCredentials: true
            },
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(form_data),                // 將物件資料(不用雙引號) 傳送到指定的 url
            dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
            // beforeSend: function(){       // 在 request 發送之前執行
            // },   
            success: function (data) {      // request 成功取得回應後執行
                console.log(data);
            },
            error: function (xhr) {         // request 發生錯誤的話執行
                console.log("error");
                console.log(xhr);
            },
            complete: function () {
                $("div.show").removeClass("show");
                location.reload();
            }

        });

        // init();
    })

    //新增申訴案件
    $(document).on("submit", "#comForm", function (event) {
        event.preventDefault();
        let setabID = $(this).find("textarea.mb_complaint").attr("estabID");
        let task_text = $(this).find("textarea.mb_complaint").val().trim();
        let comTel = $(this).find("input.mb_comTel").val();
        let form_data = {
            "comDetail": task_text,
            "comTel": comTel,
            "estabCaseID": parseInt(setabID)
        };


        $.ajax({
            url: `${API_BASE_URL}/mem-estab-case`,           // 資料請求的網址
            type: "POST",                  // GET | POST | PUT | DELETE | PATCH
            xhrFields: {
                withCredentials: true
            },
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(form_data),                // 將物件資料(不用雙引號) 傳送到指定的 url
            dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
            // beforeSend: function(){       // 在 request 發送之前執行
            // },   
            success: function (data) {      // request 成功取得回應後執行
                console.log(data);
            },
            error: function (xhr) {         // request 發生錯誤的話執行
                console.log("error");
                console.log(xhr);
            },
            complete: function () {
                $("div.show").removeClass("show");
                location.reload();
            }

        });


    });

    //修改訂單
    $(document).on("click", ".mb_estab_case_complaint_btn", function (event) {
        event.preventDefault();
        let dayCodeStr = $(".mb_mon_btn").val() + $(".mb_tue_btn").val() + "0" + $(".mb_thu_btn").val() + $(".mb_fri_btn").val() + $(".mb_sat_btn").val() + "0";
        let planOrdID = $("h5#planOrdID").attr("planOrdID");
        let timeID = $("button.mb_plan_time_btn_on").attr("rangeID");
        let form_data = {
            "day": dayCodeStr,
            "planOrdID": parseInt(planOrdID),
            "timeID": parseInt(timeID)
        };
         if(activeButtonCountWeek < parseInt($("#planForm").attr('dayCode')) && timeID == undefined){
            alert("請選擇 1 個收取時間及 "+$("#planForm").attr('dayCode')+" 個收取日");
        }else if(timeID == undefined){
            alert("請選擇收取時間");
        }else if(activeButtonCountWeek < parseInt($("#planForm").attr('dayCode'))){
            alert("請選擇 "+$("#planForm").attr('dayCode')+" 個收取日");
        }else{
            let yes = confirm("你確定要修改這筆訂單嗎?") 
            if(yes){
                $.ajax({
                    url: `${API_BASE_URL}/mem-estab-case/mem-plan-ord`,           // 資料請求的網址
                    type: "POST",                  // GET | POST | PUT | DELETE | PATCH
                    xhrFields: {
                        withCredentials: true
                    },
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify(form_data),                // 將物件資料(不用雙引號) 傳送到指定的 url
                    dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
                    beforeSend: function () {       // 在 request 發送之前執行
                            alert("你確認要修改這筆訂單?");
                    
                    },
                    success: function (data) {      // request 成功取得回應後執行
                        console.log(data);
                    },
                    error: function (xhr) {         // request 發生錯誤的話執
                        if (xhr.responseJSON.message == "Your modification limit has been reached.") {
                            alert("修改次數已達上限");
                        } else {
                            alert(xhr);
                        }

                        // console.log(xhr);
                    },
                    complete: function () {
                        $("div.show").removeClass("show");
                        location.reload();
                    }

                });
            }    
        }

    });

    //刪除訂單(方案狀態更換)
    $(document).on("click", ".mb_update_02 ", function (event) {
        console.log(11111);
        event.preventDefault();
        let planOrdID = $("h5#planOrdID").attr("planOrdID");
       
        let form_data = {
            "planOrdID": parseInt(planOrdID),
            "planStatusID": 210002
        };
        let yes = confirm("你確認要刪除這筆訂單?") 
        if(yes){

            $.ajax({
                url: `${API_BASE_URL}/mem-estab-case/mem-plan-ord-status`,           // 資料請求的網址
                type: "PATCH",                  // GET | POST | PUT | DELETE | PATCH
                xhrFields: {
                    withCredentials: true
                },
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(form_data),                // 將物件資料(不用雙引號) 傳送到指定的 url
                dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
                beforeSend: function () {       // 在 request 發送之前執行
                    
                },
                success: function (data) {      // request 成功取得回應後執行
                    console.log(data);
                },
                error: function (xhr) {         // request 發生錯誤的話執
                        alert(xhr);
                },
                complete: function () {
                    location.reload();
                }

            });
        }

    });

})


function closeModal() {
    if ($("div.show").length != 0) {
        openModal = true;
    } else {
        openModal = false;
    }
}



function readyDateTime(DateTimeString) {
    let dateObject = new Date(DateTimeString);

    let year = dateObject.getFullYear();
    let month = String(dateObject.getMonth() + 1).padStart(2, '0');
    let day = String(dateObject.getDate()).padStart(2, '0');
    let hours = String(dateObject.getHours()).padStart(2, '0');
    let minutes = String(dateObject.getMinutes()).padStart(2, '0');
    let seconds = String(dateObject.getSeconds()).padStart(2, '0');

    let formattedString = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;

    return formattedString;
}

function toIntday(srt) {
    switch (srt) {
        case '一':
            return 1;
        case '二':
            return 2;
        case '三':
            return 3;
        case '四':
            return 4;
        case '五':
            return 5;

    }
}






