<script setup>
import { onMounted } from 'vue';
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
const handleButtonClick = () => {
    document.getElementById('loadMoreButton').style.display = 'none';
    showHiddenContent();
}

const showHiddenContent = () => {
    document.querySelector('.product-hidden').style.display = 'none';
}
onMounted(() => {
    const items = document.querySelectorAll('.carousel .carousel-item');
    items.forEach((el) => {
        const minPerSlide = 4;
        let next = el.nextElementSibling;
        for (let i = 1; i < minPerSlide; i++) {
            if (!next) {
                // Wrap carousel by using first child
                next = items[0];
            }
            const cloneChild = next.cloneNode(true);
            el.appendChild(cloneChild.children[0]);
            next = next.nextElementSibling;
        }
    });
});


</script>
<template>
    <main>
        <Header></Header>
        <div class="index">
            <div class="banner">
                <img class="bannerimg" src="/src/assets/img/banner.png">
            </div>
            <div class="text">
                <h1 class="text1">FEATURES</h1>
            </div>
            <div class="productAll">
                <div class="container">
                    <div class="row mx-auto my-auto justify-content-center">
                        <div id="recipeCarousel" class="carousel slide" data-bs-ride="carousel">
                            <div class="carousel-inner" role="listbox">
                                <div class="carousel-item active">
                                    <div class="col-md-3">
                                        <div class="product">
                                            <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                            </div>
                                            <div class="icon">
                                                <div class="icon1">
                                                    <div class="background1"></div>
                                                    <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                                </div>
                                                <div class="icon2">
                                                    <div class="background2"></div>
                                                    <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                                </div>
                                            </div>
                                            <div class="information">
                                                <div class="productName">
                                                    <span>Jodan 1</span>
                                                </div>
                                                <div class="price">
                                                    <span>Price: $ 120</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item ">
                                    <div class="col-md-3">
                                        <div class="product">
                                            <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                            </div>
                                            <div class="icon">
                                                <div class="icon1">
                                                    <div class="background1"></div>
                                                    <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                                </div>
                                                <div class="icon2">
                                                    <div class="background2"></div>
                                                    <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                                </div>
                                            </div>
                                            <div class="information">
                                                <div class="productName">
                                                    <span>Jodan 2</span>
                                                </div>
                                                <div class="price">
                                                    <span>Price: $ 120</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item ">
                                    <div class="col-md-3">
                                        <div class="product">
                                            <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                            </div>
                                            <div class="icon">
                                                <div class="icon1">
                                                    <div class="background1"></div>
                                                    <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                                </div>
                                                <div class="icon2">
                                                    <div class="background2"></div>
                                                    <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                                </div>
                                            </div>
                                            <div class="information">
                                                <div class="productName">
                                                    <span>Jodan 3</span>
                                                </div>
                                                <div class="price">
                                                    <span>Price: $ 120</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item ">
                                    <div class="col-md-3">
                                        <div class="product">
                                            <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                            </div>
                                            <div class="icon">
                                                <div class="icon1">
                                                    <div class="background1"></div>
                                                    <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                                </div>
                                                <div class="icon2">
                                                    <div class="background2"></div>
                                                    <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                                </div>
                                            </div>
                                            <div class="information">
                                                <div class="productName">
                                                    <span>Jodan 4</span>
                                                </div>
                                                <div class="price">
                                                    <span>Price: $ 120</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item ">
                                    <div class="col-md-3">
                                        <div class="product">
                                            <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                            </div>
                                            <div class="icon">
                                                <div class="icon1">
                                                    <div class="background1"></div>
                                                    <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                                </div>
                                                <div class="icon2">
                                                    <div class="background2"></div>
                                                    <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                                </div>
                                            </div>
                                            <div class="information">
                                                <div class="productName">
                                                    <span>Jodan 5</span>
                                                </div>
                                                <div class="price">
                                                    <span>Price: $ 120</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <a class="carousel-control-prev bg-transparent w-aut" href="#recipeCarousel" role="button"
                                data-bs-slide="prev">
                                <span class="carousel-control-prev-icon iconnext" aria-hidden="true"></span>
                            </a>
                            <a class="carousel-control-next bg-transparent w-aut right" href="#recipeCarousel"
                                role="button" data-bs-slide="next">
                                <span class="carousel-control-next-icon iconnext " aria-hidden="true"></span>
                            </a>
                        </div>
                    </div>
                    <div class="categoryText">
                        <h1>Category</h1>
                    </div>
                    <div class="category">
                        <div class="category-item">
                            <a href="#"><span>
                                    New Arrival
                                </span></a>
                        </div>
                        <div class="category-item">
                            <a href="#"><span>
                                    Best Seller
                                </span></a>
                        </div>
                        <div class="category-item">
                            <a href="#"><span>
                                    Feature
                                </span></a>
                        </div>
                        <div class="category-item">
                            <a href="#"><span>
                                    Special Offer
                                </span></a>
                        </div>
                        <div class="category-item">
                            <a href="#"><span>
                                    More
                                </span></a>
                        </div>
                    </div>
                    <div class="product-item">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="product">
                                    <div class="imageProduct"><img src="/src/assets/img/product.png" alt="">
                                    </div>
                                    <div class="icon-item">
                                        <div class="icon1">
                                            <div class="background1"></div>
                                            <div class="iconbag"><img src="/src/assets/img/bag.png"></div>
                                        </div>
                                        <div class="icon2">
                                            <div class="background2"></div>
                                            <div class="iconheart"><img src="/src/assets/img/heart.png"></div>
                                        </div>
                                    </div>
                                    <div class="information">
                                        <div class="productName">
                                            <span>Jodan 1</span>
                                        </div>
                                        <div class="price">
                                            <span>Price: $ 120</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="product-hidden"></div>
                    <div class="button-product">
                        <button class="button-item" id="loadMoreButton" @click="handleButtonClick()">Load More</button>
                    </div>
                </div>
                <div class="trending">
                    <div class="tr1">
                        <div class="textTrending">
                            <h1>TRENDING</h1>
                        </div>
                        <div class="portTrend">
                            <div class="back">
                                <img src="../assets/img/orcle.png" alt="">
                            </div>
                            <div class="productNo">
                                <img src="../assets/img/productItem.png" alt="">
                            </div>
                        </div>
                        <div class="discount">
                            <span class="title1">
                                GET UP TO 30% OFF
                            </span><br>
                            <span>
                                You can get 30% off this product
                                if you are buying now
                            </span>
                        </div>
                    </div>
                    <div class="tr2">
                        <div class="textTrend">
                            <div class="textTrend1">
                                <span>Purchase your
                                    shoes now.</span>

                            </div>
                            <div class="textTrend2">
                                <span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Dolor morbi velit turpis
                                    et
                                    dolor sit morbi odio id. Aliquam ultricies tortor ac.</span>
                            </div>
                        </div>
                        <div class="buttonTe">
                            <button type="submit"><span>Buy now</span></button>
                        </div>
                    </div>
                </div>
                <div class="new">
                    <div class="textNew">
                        <h1>
                            NEWS
                        </h1>
                    </div>
                    <div class="pro">
                        <div class="container">
                            <div class="row mx-auto my-auto justify-content-center">
                                <div id="recipeCarousel" class="carousel1 slide" data-bs-ride="carousel">
                                    <div class="carouse-inner newproduct" role="listbox">
                                        <div class="carousel-item1 active">
                                            <div class="col-md-3">
                                                <div class="backnew">
                                                    <div class="productnew">
                                                        <img src="../assets/img/newproduct.png" alt="">
                                                    </div>
                                                    <div class="read-more">
                                                        <a href="">Read more</a>
                                                    </div>
                                                    <div class="text-new">
                                                        <h2>23% off in all products</h2>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="carousel-item1">
                                            <div class="col-md-3">
                                                <div class="backnew">
                                                    <div class="productnew">
                                                        <img src="../assets/img/newproduct.png" alt="">
                                                    </div>
                                                    <div class="read-more">
                                                        <a href="">Read more</a>
                                                    </div>
                                                    <div class="text-new">
                                                        <h2>23% off in all products</h2>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="carousel-item1">
                                            <div class="col-md-3">
                                                <div class="backnew">
                                                    <div class="productnew">
                                                        <img src="../assets/img/newproduct.png" alt="">
                                                    </div>
                                                    <div class="read-more">
                                                        <a href="">Read more</a>
                                                    </div>
                                                    <div class="text-new">
                                                        <h2>23% off in all products</h2>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <Footer></Footer>
    </main>
</template>
<script>


</script>
<style>
@import url('/src/assets/css/index.css');
</style>