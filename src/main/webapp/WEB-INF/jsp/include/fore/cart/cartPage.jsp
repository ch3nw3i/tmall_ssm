<!-- 模仿天猫整站ssm 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>


<script>
    var deleteCartItem = false;
    var deleteCartItemId = 0;
    $(function () {
        $("a.deleteCartItem").click(function () {
            deleteCartItem = false;
            var cartid = $(this).attr("cartid")
            deleteCartItemId = cartid;
            $("#deleteConfirmModal").modal('show');
        });
        $("button.deleteConfirmButton").click(function () {
            deleteCartItem = true;
            $("#deleteConfirmModal").modal('hide');
        });

        $('#deleteConfirmModal').on('hidden.bs.modal', function (e) {
            if (deleteCartItem) {
                var page = "foredeleteCart";
                $.post(
                    page,
                    {"id": deleteCartItemId},
                    function (result) {
                        if ("success" == result) {
                            $("tr.cartProductItemTR[cartid=" + deleteCartItemId + "]").hide();
                            location.reload();
                        } else {
                            location.href = "loginPage";
                        }
                    }
                );

            }
        })

        $("img.cartProductItemIfSelected").click(function () {
            var selectit = $(this).attr("selectit")
            if ("selectit" == selectit) {
                $(this).attr("src", "img/site/cartNotSelected.png");
                $(this).attr("selectit", "false")
                $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
            } else {
                $(this).attr("src", "img/site/cartSelected.png");
                $(this).attr("selectit", "selectit")
                $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
            }
            syncSelect();
            syncCreateOrderButton();
            calcCartSumPriceAndNumber();
        });
        $("img.selectAllItem").click(function () {
            var selectit = $(this).attr("selectit")
            if ("selectit" == selectit) {
                $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
                $("img.selectAllItem").attr("selectit", "false")
                $(".cartProductItemIfSelected").each(function () {
                    $(this).attr("src", "img/site/cartNotSelected.png");
                    $(this).attr("selectit", "false");
                    $(this).parents("tr.cartProductItemTR").css("background-color", "#fff");
                });
            } else {
                $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
                $("img.selectAllItem").attr("selectit", "selectit")
                $(".cartProductItemIfSelected").each(function () {
                    $(this).attr("src", "img/site/cartSelected.png");
                    $(this).attr("selectit", "selectit");
                    $(this).parents("tr.cartProductItemTR").css("background-color", "#FFF8E1");
                });
            }
            syncCreateOrderButton();
            calcCartSumPriceAndNumber();
        });

        // 手动输入商品数量
        $(".orderItemNumberSetting").keyup(function () {
            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            num = parseInt(num);
            if (isNaN(num))
                num = 1;
            if (num <= 0)
                num = 1;
            if (num > stock)
                num = stock;
            syncPrice(pid, num, price);
        });

		// 商品数量 加一
		$(".numberPlus").click(function () {
            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            num++;
            if (num > stock)
                num = stock;
            syncPrice(pid, num, price);
        });

		// 商品数量 减一
		$(".numberMinus").click(function () {
            var pid = $(this).attr("pid");
            var stock = $("span.orderItemStock[pid=" + pid + "]").text();
            var price = $("span.orderItemPromotePrice[pid=" + pid + "]").text();
            var num = $(".orderItemNumberSetting[pid=" + pid + "]").val();
            --num;
            if (num <= 0)
                num = 1;
            syncPrice(pid, num, price);
        });

        $("button.createOrderButton").click(function () {
            var params = "";
            $(".cartProductItemIfSelected").each(function () {
                if ("selectit" == $(this).attr("selectit")) {
                    var cartid = $(this).attr("cartid");
                    params += "&id=" + cartid;
                }
            });
            params = params.substring(1);
            location.href = "forebuy?" + params;
        });
    })

    function syncCreateOrderButton() {
        var selectAny = false;
        $(".cartProductItemIfSelected").each(function () {
            if ("selectit" == $(this).attr("selectit")) {
                selectAny = true;
            }
        });
        if (selectAny) {
            $("button.createOrderButton").css("background-color", "#C40000");
            $("button.createOrderButton").removeAttr("disabled");
        } else {
            $("button.createOrderButton").css("background-color", "#AAAAAA");
            $("button.createOrderButton").attr("disabled", "disabled");
        }
    }

    function syncSelect() {
        var selectAll = true;
        $(".cartProductItemIfSelected").each(function () {
            if ("false" == $(this).attr("selectit")) {
                selectAll = false;
            }
        });
        if (selectAll)
            $("img.selectAllItem").attr("src", "img/site/cartSelected.png");
        else
            $("img.selectAllItem").attr("src", "img/site/cartNotSelected.png");
    }

    // 计算购物车中选中商品的总价格价格和总数量
    function calcCartSumPriceAndNumber() {
        var sum = 0;
        var totalNumber = 0;
        $("img.cartProductItemIfSelected[selectit='selectit']").each(function () {
            var cartid = $(this).attr("cartid");
            var price = $(".cartProductItemSmallSumPrice[cartid=" + cartid + "]").text();
            price = price.replace(/,/g, "");
            price = price.replace(/￥/g, "");
            sum += new Number(price);
            var num = $(".orderItemNumberSetting[cartid=" + cartid + "]").val();
            totalNumber += new Number(num);
        });
        $("span.cartSumPrice").html("￥" + formatMoney(sum));
        $("span.cartTitlePrice").html("￥" + formatMoney(sum));
        $("span.cartSumNumber").html(totalNumber);
    }

    // 同步价格
    function syncPrice(pid, num, price) {
        $(".orderItemNumberSetting[pid=" + pid + "]").val(num);
        var cartProductItemSmallSumPrice = formatMoney(num * price);
        $(".cartProductItemSmallSumPrice[pid=" + pid + "]").html("￥" + cartProductItemSmallSumPrice);
        calcCartSumPriceAndNumber();
		// 异步 改变购物车数据表的商品数量
        var page = "forechangeCartItem";
        $.post(
            page,
            {"pid": pid, "number": num},
            function (result) {
                if ("success" != result) {
                    location.href = "loginPage";
                }
            }
        );
    }
</script>

<title>购物车</title>
<div class="cartDiv">
    <div class="cartTitle pull-right">
        <span>已选商品  (不含运费)</span>
        <span class="cartTitlePrice">￥0.00</span>
        <button class="createOrderButton" disabled="disabled">结 算</button>
    </div>
    <div class="cartProductList">
        <table class="cartProductTable">
            <thead>
                <tr>
                    <th class="selectAndImage">
                        <img selectit="false" class="selectAllItem" src="img/site/cartNotSelected.png">
                        全选
                    </th>
                    <th>商品信息</th>
                    <th>单价</th>
                    <th>数量</th>
                    <th width="120px">金额</th>
                    <th class="operation">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${cartList}" var="cart">
                    <tr cartid="${cart.id}" class="cartProductItemTR">
                        <td>
                            <img selectit="false" cartid="${cart.id}" class="cartProductItemIfSelected"
                                 src="img/site/cartNotSelected.png">
                            <a style="display:none" href="#nowhere"><img src="img/site/cartSelected.png"></a>
                            <img class="cartProductImg"
                                 src="img/productSingle_middle/${cart.product.firstProductImage.id}.jpg">
                        </td>
                        <td>
                            <div class="cartProductLinkOutDiv">
                                <a href="foreproduct?pid=${cart.product.id}"
                                   class="cartProductLink">${cart.product.name}</a>
                                <div class="cartProductLinkInnerDiv">
                                    <img src="img/site/creditcard.png" title="支持信用卡支付">
                                    <img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
                                    <img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">
                                </div>
                            </div>
                        </td>
                        <td>
                            <span class="cartProductItemOringalPrice">￥${cart.product.originalPrice}</span>
                            <span class="cartProductItemPromotionPrice">￥${cart.product.promotePrice}</span>
                        </td>
                        <td>
                            <div class="cartProductChangeNumberDiv">
                                <span class="hidden orderItemStock "
                                      pid="${cart.product.id}">${cart.product.stock}</span>
                                <span class="hidden orderItemPromotePrice "
                                      pid="${cart.product.id}">${cart.product.promotePrice}</span>
                                <a pid="${cart.product.id}" class="numberMinus" href="#nowhere">-</a>
                                <input pid="${cart.product.id}" cartid="${cart.id}" class="orderItemNumberSetting"
                                       autocomplete="off" value="${cart.number}">
                                <a stock="${cart.product.stock}" pid="${cart.product.id}" class="numberPlus"
                                   href="#nowhere">+</a>
                            </div>
                        </td>
                        <td>
							<span class="cartProductItemSmallSumPrice" cartid="${cart.id}" pid="${cart.product.id}">
							￥<fmt:formatNumber type="number" value="${cart.product.promotePrice*cart.number}"
                                               minFractionDigits="2"/>
							</span>
                        </td>
                        <td>
                            <a class="deleteCartItem" cartid="${cart.id}" href="#nowhere">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
    </div>

    <div class="cartFoot">
        <img selectit="false" class="selectAllItem" src="img/site/cartNotSelected.png">
        <span>全选</span>
        <!-- 		<a href="#">删除</a> -->
        <div class="pull-right">
            <span>已选商品 <span class="cartSumNumber">0</span> 件</span>
            <span>合计 (不含运费): </span>
            <span class="cartSumPrice">￥0.00</span>
            <button class="createOrderButton" disabled="disabled">结 算</button>
        </div>

    </div>

</div>