<script setup>
import Header from '../components/Header.vue';
import Footer from '@/components/Footer.vue';
import { onMounted } from 'vue';
onMounted(() => {
    let items = document.querySelectorAll('.carousel .carousel-item')

    items.forEach((el) => {
        const minPerSlide = 6
        let next = el.nextElementSibling
        for (var i = 1; i < minPerSlide; i++) {
            if (!next) {
                // wrap carousel by using first child
                next = items[0]
            }
            let cloneChild = next.cloneNode(true)
            el.appendChild(cloneChild.children[0])
            next = next.nextElementSibling
        }
    })
});
</script>
<template>
    <main>
        <Header></Header>
        <div class="cart">
            <div class="textcart">
                <div class="text5">
                    <span>Shopping Cart</span>
                </div>
                <div class="text6">
                    <span>Items will be shipped to you as soon as the seller has specified. Possible delays due to
                        holidays.</span>
                </div>
            </div>
            <div class="chia">
                <div class="listcart">
                    <div class="cartItem">
                        <div class="imgcart">
                            <div class="heartcart">
                                <img src="../assets/img/heart.png" alt="">
                            </div>
                            <div class="imganh">
                                <img src="../assets/img/product1.png" alt="">
                            </div>
                        </div>
                        <div class="cartaction">
                            <div class="productname">
                                <div class="tenproduct">
                                    <span class="textname">
                                        Blaze Mid 77 - White/Blue
                                    </span><br>
                                    <span class="textdescripstion">
                                        Custom made size 39 womens
                                    </span>
                                </div>
                                <div class="deletecart">
                                    <a href=""><img src="../assets/img/deletecart.png" alt=""></a>
                                </div>
                            </div>
                            <div class="action">
                                <div class="priceproduct">
                                    <span class="textprice">
                                        $ 200
                                    </span>
                                </div>
                                <div class="addcart">
                                    <input type="text" placeholder="1">
                                    <a href=""><img src="../assets/img/iconcart.png"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cartItem">
                        <div class="imgcart">
                            <div class="heartcart">
                                <img src="../assets/img/heart.png" alt="">
                            </div>
                            <div class="imganh">
                                <img src="../assets/img/product1.png" alt="">
                            </div>
                        </div>
                        <div class="cartaction">
                            <div class="productname">
                                <div class="tenproduct">
                                    <span class="textname">
                                        Blaze Mid 77 - White/Blue
                                    </span><br>
                                    <span class="textdescripstion">
                                        Custom made size 39 womens
                                    </span>
                                </div>
                                <div class="deletecart">
                                    <a href=""><img src="../assets/img/deletecart.png" alt=""></a>
                                </div>
                            </div>
                            <div class="action">
                                <div class="priceproduct">
                                    <span class="textprice">
                                        $ 200
                                    </span>
                                </div>
                                <div class="addcart">
                                    <input type="text" placeholder="1">
                                    <a href=""><img src="../assets/img/iconcart.png"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cartItem">
                        <div class="imgcart">
                            <div class="heartcart">
                                <img src="../assets/img/heart.png" alt="">
                            </div>
                            <div class="imganh">
                                <img src="../assets/img/product1.png" alt="">
                            </div>
                        </div>
                        <div class="cartaction">
                            <div class="productname">
                                <div class="tenproduct">
                                    <span class="textname">
                                        Blaze Mid 77 - White/Blue
                                    </span><br>
                                    <span class="textdescripstion">
                                        Custom made size 39 womens
                                    </span>
                                </div>
                                <div class="deletecart">
                                    <a href=""><img src="../assets/img/deletecart.png" alt=""></a>
                                </div>
                            </div>
                            <div class="action">
                                <div class="priceproduct">
                                    <span class="textprice">
                                        $ 200
                                    </span>
                                </div>
                                <div class="addcart">
                                    <input type="text" placeholder="1">
                                    <a href=""><img src="../assets/img/iconcart.png"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cartItem">
                        <div class="imgcart">
                            <div class="heartcart">
                                <img src="../assets/img/heart.png" alt="">
                            </div>
                            <div class="imganh">
                                <img src="../assets/img/product1.png" alt="">
                            </div>
                        </div>
                        <div class="cartaction">
                            <div class="productname">
                                <div class="tenproduct">
                                    <span class="textname">
                                        Blaze Mid 77 - White/Blue
                                    </span><br>
                                    <span class="textdescripstion">
                                        Custom made size 39 womens
                                    </span>
                                </div>
                                <div class="deletecart">
                                    <a href=""><img src="../assets/img/deletecart.png" alt=""></a>
                                </div>
                            </div>
                            <div class="action">
                                <div class="priceproduct">
                                    <span class="textprice">
                                        $ 200
                                    </span>
                                </div>
                                <div class="addcart">
                                    <input type="text" placeholder="1">
                                    <a href=""><img src="../assets/img/iconcart.png"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cartItem">
                        <div class="imgcart">
                            <div class="heartcart">
                                <img src="../assets/img/heart.png" alt="">
                            </div>
                            <div class="imganh">
                                <img src="../assets/img/product1.png" alt="">
                            </div>
                        </div>
                        <div class="cartaction">
                            <div class="productname">
                                <div class="tenproduct">
                                    <span class="textname">
                                        Blaze Mid 77 - White/Blue
                                    </span><br>
                                    <span class="textdescripstion">
                                        Custom made size 39 womens
                                    </span>
                                </div>
                                <div class="deletecart">
                                    <a href=""><img src="../assets/img/deletecart.png" alt=""></a>
                                </div>
                            </div>
                            <div class="action">
                                <div class="priceproduct">
                                    <span class="textprice">
                                        $ 200
                                    </span>
                                </div>
                                <div class="addcart">
                                    <input type="text" placeholder="1">
                                    <a href=""><img src="../assets/img/iconcart.png"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="caculrator">
                    <div class="textOrder">
                        Order Summary
                    </div>
                    <div class="discoutcode">
                        <div class="textdis">
                            <span>Discount code:</span>
                        </div>
                        <div class="code">
                            <input type="text" placeholder="SAVE20">
                            <button type="submit">Apply</button>
                        </div>
                    </div>
                    <div class="discripstion">
                        <div class="tieudethanhtoan">
                            <span>Order value :</span><br>
                            <span>VAT :</span><br>
                            <span>Total before discount :</span><br>
                        </div>
                        <div class="thanhtoan">
                            <span>$ 1800</span><br>
                            <span>$ 90</span><br>
                            <span>$ 2000</span><br>
                        </div>
                    </div>
                    <div class="total">
                        <div class="textTotal">
                            <span>TOTAL :</span>
                        </div>
                        <div class="gtTotal">
                            <span>$ 1890</span>
                        </div>
                    </div>
                    <div class="button-total">
                        <button type="submit"><span>Buy now</span></button>
                    </div>

                </div>
            </div>
        </div>
        <div class="backtoindex">
            <div class="iconback">
                <a href=""><img src="../assets/img/back.png" alt=""></a>
            </div>
            <div class="textback">
                <span>
                    Continue Shopping
                </span>
            </div>
        </div>
        <div class="last">
            <div class="container">
                <div class="textlast">
                    Similar Items You Might Also Like
                </div>
                <div class="row mx-auto my-auto justify-content-center">
                    <div id="recipeCarousel" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-inner" role="listbox">
                            <div class="carousel-item active">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 1</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 2</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 3</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 4</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 5</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 6</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 7</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 8</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <a class="carousel-control-prev bg-transparent w-aut lefticon" href="#recipeCarousel" role="button"
                        data-bs-slide="prev">
                        <span class="carousel-control-prev-icon iconnext" aria-hidden="true"></span>
                    </a>
                    <a class="carousel-control-next bg-transparent w-aut righticon" href="#recipeCarousel" role="button"
                        data-bs-slide="next">
                        <span class="carousel-control-next-icon iconnext " aria-hidden="true"></span>
                    </a>
                </div>
            </div>

        </div>
        <Footer></Footer>
    </main>
</template>
<style>
@import url('/src/assets/css/cart.css');
</style>