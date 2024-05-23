<template>
    <TheHeader></TheHeader>
    <div class="section__product" style="margin-bottom: 300px;">

        <div class="section__product_left">
            <div class="section__product_left__top">
                <div class="section__product__left__top__image">
                    <img :src="curentImage" alt="">

                </div>
                <div class="section__product_left__top__tags">
                    <button>NEWS</button>
                    <button class="red">TRENDING</button>
                </div>
            </div>
            <div class="section__product_left__bottom">

                <div class="section__product_left__bottom__thumbnails">
                    <div v-for="(item, index) in findImageDescription" :key="index"
                        class="section__product_left__bottom__item">
                        <img :src="getImageUrl(item.image)" alt="">
                    </div>
                </div>
            </div>
        </div>
        <div class="section__product__right">
            <div class="section__product__right__top">
                <div class="section__product__right__top__menu">
                    <p><a href="">Home</a> &gt; <a href="">Lifestyple</a> &gt; <a href="">Snekaer</a> &gt; <a
                            href="">Joedan</a> &gt; <a href="">Nike Blaze</a>
                    </p>
                </div>
                <div class="section__product__right__top__overview">
                    <div class="section__product__right__top__overview__left">
                        <h3>{{ product?.productName }}</h3>
                        <span>{{ product?.category?.categoryName }}</span>
                    </div>
                    <div class="section__product__right__top__overview__right">
                        <div class="section__product__right__top__overview__right__wishlist">
                            <img src="../assets/img/WishList.png" alt="">
                            <span>109</span>
                        </div>
                        <img src="../assets/img/Save.png" alt="">
                        <img src="../assets/img/Share.png" alt="">
                    </div>
                </div>
                <div class="section__product__right__top__infos">
                    <div class="section__product__right__top__infos__price">
                        <h3>{{ totalPriceAfterDiscount }}</h3>
                        <h2>{{ product?.price }}VND</h2>
                    </div>
                    <div class="section__product__right__top__infos__review">
                        <div class="section__product__right__top__infos__review__item">
                            <div class="section__product__right__top__infos__review__rating">
                                <img src="../assets/img/Rating.png" alt="">
                                <span>4.8</span>
                            </div>
                            <div class="section__product__right__top__infos__review__feedback">
                                <img src="../assets/img/Feedback.png" alt="">
                                <span>67 </span>
                                <span>Reviews</span>
                            </div>
                        </div>
                        <p><span>93%</span> of buyers have recommended this</p>
                    </div>
                </div>
            </div>
            <div class="section__product__right__info">
                <div class="section__product__right__info__colors">
                    <h3>Choose a Color</h3>
                    <div class="section__product__right__info__colors--select">
                        <button v-for="(color, index) in colors" :key="index" style="border-color: black;"
                            :style="{ backgroundColor: color.color, outline: selectedColor === color.name ? `3px solid ${color.color}` : 'none' }"
                            @click="setColor(color.name)">
                            <img v-if="color.name === selectedColor" class="tick" src="../assets/img/tick.png" alt="">
                        </button>
                        <div value="selected">
                            <img src="../assets/images/Selected.png" alt="">
                        </div>
                    </div>
                </div>
                <div class="section__product__right__info__sizes">
                    <h3>Choose a Size</h3>
                    <div class="section__product__right__info__sizes--select">
                        <v-radio-group v-model="selectedSize" inline @change="setSize(selectedSize)">
                            <v-radio class="label" v-for="(item, index) in sizes" :key="index" color="info"
                                :label="item.sizeName" :value="item"></v-radio>
                        </v-radio-group>

                    </div>
                </div>
                <div class="add">
                    <div class="add_amount">
                        <span @click="this.setAmount(-1)">-</span>
                        <span id="amount">{{ amount }}</span>
                        <span @click="this.setAmount(+1)">+</span>
                    </div>
                    <button type="submit" @click="addToCart">
                        <img src="../assets/img/CartWhile.png" alt="">
                        Add To Cart
                    </button>
                </div>
            </div>
            <div class="section__product__right__bottom">
                <div class="section__product__right__bottom__free">
                    <img src="../assets/img/Delivetor.png" alt="">
                    <div>
                        <h3>Free Delivery</h3>
                        <label for="">Enter your Postal code for Delivery Availability</label>
                        <input type="text">
                    </div>
                </div>
                <div class="section__product__right__bottom__return">
                    <img src="../assets/img/CarRed.png" alt="">
                    <div>
                        <h3>Return Delivery</h3>
                        <span>Free 30 days Delivery Return. <a href="#">Details</a></span>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <TheFooter></TheFooter>
</template>
<script>
import { ref, onMounted, onUpdated, watch } from 'vue';
import TheHeader from '../components/Header.vue';
import TheFooter from '../components/Footer.vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';

export default {
    name: 'Detail',
    components: {
        TheHeader,
        TheFooter,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const productId = ref(route.params.id);
        const curentImage = ref('');
        const selectedColor = ref(null);
        const selectedSize = ref(null);
        const sizes = ref([]);
        const ex7 = ref('red');
        const amount = ref(1);
        const images = ref({});
        const product = ref(null);
        const productDetail = ref(null);
        const productDetailByColorNameAndSizeName = ref(null);
        const findImageDescription = ref([]);
        const colors = ref([]);
        const totalPriceAfterDiscount = ref(0);
        const userName = ref('');

        const colorMapping = {
            'Red': 'rgba(223, 56, 50, 1)',
            'Blue': 'rgba(60, 173, 212, 1)',
            'Black': 'rgba(24, 24, 26, 1)',
            'White': 'rgba(238, 238, 238, 1)',
            'Dark': 'rgba(116, 99, 82, 1)'
        };

        const fetchColors = async () => {
            try {
                const response = await axios.get('http://localhost:8080/color');
                const colorData = response.data;
                const colorsArray = colorData.map(color => {
                    const colorName = color.colorName;
                    if (colorName in colorMapping) {
                        return {
                            color: colorMapping[colorName],
                            name: colorName,
                            id: color.id,
                            status: color.status
                        };
                    } else {
                        return {
                            color: 'rgba(0, 0, 0, 0)',
                            name: colorName,
                            id: color.id,
                            status: color.status
                        };
                    }
                });

                colors.value = colorsArray;
            } catch (error) {
                console.error('Error fetching colors:', error);
            }
        };

        const setSize = async (size) => {
            selectedSize.value = size;
        };

        const setColor = async (color) => {
            selectedColor.value = color;
        };

        const setAmount = (value) => {
            if (amount.value + value >= 0) {
                amount.value += value;
            }
        };

        onMounted(() => {
            fetchColors();
        });

        const fetchSizes = async () => {
            try {
                const response = await axios.get('http://localhost:8080/size');
                sizes.value = response.data;
            } catch (error) {
                console.error('Error fetching sizes:', error);
            }
        };

        onMounted(fetchSizes);

        const features = ref([
            { isVisible: true, image: "../assets/img/anh5.png", name: "Jordan", price: 500, rating: 1 },
            { isVisible: false, image: "../assets/img/anh2.png", name: "Nike", price: 400, rating: 3.5 },
            { isVisible: true, image: "../assets/img/anh3.png", name: "Jordan", price: 500, rating: 4 },
            { isVisible: false, image: "../assets/img/anh4.png", name: "Nike", price: 400, rating: 2.5 },
            { isVisible: true, image: "../assets/img/anh5.png", name: "Jordan", price: 500, rating: 4.5 },
            { isVisible: false, image: "../assets/img/anh2.png", name: "Nike", price: 400, rating: 5 },
            { isVisible: false, image: "../assets/img/anh3.png", name: "Nike", price: 400, rating: 2.5 },
            { isVisible: true, image: "../assets/img/anh4.png", name: "Jordan", price: 500, rating: 4.5 },
            { isVisible: false, image: "../assets/img/anh4.png", name: "Nike", price: 400, rating: 10 },
        ]);

        const fetchImages = async () => {
            const imagesContext = import.meta.glob('../assets/img/*.png');
            const imageKeys = Object.keys(imagesContext);
            const imageFiles = await Promise.all(imageKeys.map(key => imagesContext[key]()));

            imageKeys.forEach((key, index) => {
                images.value[key.replace(/^.*[\\/]/, '').split('.')[0]] = imageFiles[index].default;
            });
        };

        const getProductById = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/product/get/${productId.value}`);
                product.value = response.data;
                curentImage.value = `http://localhost:8080/uploads/${product.value.image}`;
            } catch (error) {
                console.error('Error:', error);
            }
        };

        const getProductDetailByProductId = async () => {
            try {
                const response = await axios.get(` http://localhost:8080/productDetail/findProductDetailByProductId/${productId.value}`);
                productDetail.value = response.data;
            } catch (error) {
                console.error('Error:', error);
            }
        };

        const callBackend = async () => {
            try {
                if (selectedColor.value && selectedSize.value) {
                    const response = await axios.get('http://localhost:8080/productDetail/findByColorNameAndSizeName', {
                        params: {
                            productId: productId.value,
                            colorName: selectedColor.value,
                            sizeName: selectedSize.value.sizeName
                        }
                    });
                    if (response.data) {
                        productDetailByColorNameAndSizeName.value = response.data;
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Thông báo',
                            text: 'Mong quý khách thông cảm! Sản phẩm này hiện tại đang hết hàng, mời quý khách lựa chọn sản phẩm khác!',
                            confirmButtonText: 'OK'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                selectedColor.value = null;
                                selectedSize.value = null;
                            }
                        });
                    }
                }
            } catch (error) {
                console.error('Error', error);
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'An error occurred while fetching data from the API!',
                    confirmButtonText: 'OK'
                });
            }
        };

        const callBackend1 = async () => {
            try {
                if (selectedColor.value && selectedSize.value) {
                    const response = await axios.get('http://localhost:8080/productDetail/findImageDescription', {
                        params: {
                            productId: productId.value,
                            colorName: selectedColor.value,
                            sizeName: selectedSize.value.sizeName
                        }
                    });
                    findImageDescription.value = response.data;
                    console.log(response.data);
                }
            } catch (error) {
                console.error('Error', error);
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'An error occurred while fetching data from the API!',
                    confirmButtonText: 'OK'
                });
            }
        };

        const getImageUrl = (relativePath) => {
            console.log(relativePath);
            return `http://localhost:8080/uploads/${relativePath}`;
        };
        const calculateTotal = () => {
            if (product.value) {
                const price = product.value.price;
                const discount = productDetailByColorNameAndSizeName.value?.discount ? productDetailByColorNameAndSizeName.value?.discount : 0;
                totalPriceAfterDiscount.value = price * (1 - discount / 100);
            }
        };

        const decodeToken = (token) => {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
            return JSON.parse(jsonPayload);
        };

        const isLoggedIn = () => {
            const token = localStorage.getItem('token');
            if (!token) {
                return false;
            }
            const decodedToken = decodeToken(token);
            userName.value = decodedToken.sub;
            return !!token;
        };

        const addToCart = async () => {
            if (!isLoggedIn()) {
                router.push('/login');
                return;
            }
            try {
                const response = await axios.post('http://localhost:8080/cart/add', null, {
                    params: {
                        productId: productId.value,
                        colorName: selectedColor.value,
                        sizeName: selectedSize.value.sizeName,
                        userName: userName.value,
                        amout: amount.value
                    }
                });
                if (response.status === 200) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Thành công',
                        text: 'Sản phẩm đã được thêm vào giỏ hàng!',
                        confirmButtonText: 'OK'
                    });
                    router.push('/cart')
                } else {
                    throw new Error('Failed to add product to cart');
                }
            } catch (error) {
                console.error('Error adding to cart:', error);
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Đã xảy ra lỗi khi thêm sản phẩm vào giỏ hàng!',
                    confirmButtonText: 'OK'
                });
            }
        };

        onMounted(callBackend);
        onMounted(getProductDetailByProductId);
        onMounted(getProductById);
        onMounted(fetchImages);
        onMounted(callBackend1);
        onMounted(async () => {
            let items = document.querySelectorAll('.carousel .carousel-item');
            items.forEach((el) => {
                const minPerSlide = 6;
                let next = el.nextElementSibling;
                for (let i = 1; i < minPerSlide; i++) {
                    if (!next) {
                        next = items[0];
                    }
                    let cloneChild = next.cloneNode(true);
                    el.appendChild(cloneChild.children[0]);
                    next = next.nextElementSibling;
                }
            });
        });

        onUpdated(async () => {
            await callBackend();
            calculateTotal();
        });

        watch([selectedColor, selectedSize],async ()=>{
     
            await callBackend1();
        })

        return {
            getImageUrl,
            findImageDescription,
            addToCart,
            totalPriceAfterDiscount,
            productDetail,
            product,
            curentImage,
            selectedColor,
            selectedSize,
            productDetailByColorNameAndSizeName,
            ex7,
            amount,
            colors,
            sizes,
            images,
            setSize,
            setColor,
            setAmount,
            features
        };
    }
};
</script>


<style scoped>
/* section product */
.section__product {

    display: flex;
    width: 100%;
    margin: 0 auto;
    gap: 89px;
    justify-content: space-between;
    background: rgba(255, 255, 255, 1);
    margin-top: 65px;
}

.section__product_left {
    width: 50%;
    height: 100%;
    display: flex;
    flex-direction: column;
    gap: 75px;
    justify-content: center;
    align-items: center;
    position: relative;
}

.section__product_left__top {

    width: 100%;
    height: 70%;
}



.section__product__left__top__image img {
    display: flex;
    justify-content: center;
    border: 1px solid rgba(237, 240, 248, 1);
    border-radius: 17px;
    width: 120%;
    height: 641px;
    margin-left: -30%;
}

.section__product_left__top__tags {
    position: absolute;
    left: -140px;
    top: 17px;
    display: flex;
    gap: 10px
}

.section__product_left__top__tags button {
    box-sizing: content-box;
    font-family: Mulish;
    font-size: 10px;
    font-weight: 700;
    line-height: 12.55px;
    text-align: center;
    width: 70px;
    height: 32px;
    padding: 5px 15px 5px 15px;
    gap: 10px;
    border-radius: 10px;
    border: 1px solid rgba(78, 74, 242, 1);
    opacity: 0px;
    color: white;
    background-color: rgba(107, 157, 251, 1);
}

.section__product_left__top__tags .red {
    background-color: rgba(237, 97, 97, 1);
}

.section__product_left__bottom {
    width: 100%;
    height: calc(30% - 75px);
    position: relative;
    object-fit: cover;
    flex: left;
}

.section__product_left__bottom__thumbnails {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: -40%;
    gap: 18px;
}

.section__product_left__bottom .arrow__left {
    font-size: 30px;
    position: absolute;
    left: -31%;
    top: 30%;
}

.section__product_left__bottom .arrow__right {
    object-fit: cover;
    box-sizing: border-box;
    font-size: 30px;
    position: absolute;
    right: 10%;
    top: 30%;
}

.section__product_left__bottom__item {
    display: flex;
    justify-content: center;
    align-items: center;
}



.section__product_left__bottom__item img {
    width: 115px;
    height: 115px;
    border-radius: 14px;
    border: 3px solid linear-gradient(136.22deg, #3A4980 2.59%, rgba(44, 48, 121, 0.2) 97.48%);
}

.active {
    border: 1px solid rebeccapurple;
    border-radius: 14px;
    box-shadow: 1px 0px 1px 2px #999999;
}

.section__product__right__top__menu p,
a {
    text-decoration: none;
    opacity: 0px;
    font-family: Mulish;
    font-size: 14px;
    font-weight: 400;
    line-height: 17.57px;
    text-align: left;
    color: rgba(163, 169, 194, 1);
    padding: 0px 8px;
    padding-top: 4%;
}

.section__product__right__top__menu {

    width: auto;
    height: 44px;
    display: flex;
    align-items: center;
    gap: 14px;
    border-radius: 8px 0px 0px 0px;
    background: rgba(237, 240, 248, 1);


}

.section__product__right__top__overview {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    margin-bottom: 15px;

}

.section__product__right__top__overview__left h3 {
    font-family: Mulish;
    font-size: 32px;
    font-weight: 700;
    line-height: 40.16px;
    text-align: left;



}

.section__product__right__top__overview__left span {
    font-family: Mulish;
    font-size: 16px;
    font-weight: 400;
    line-height: 20.08px;
    text-align: left;
    color: rgba(185, 187, 191, 1);
    margin-top: 11px;
    display: block;
}

.section__product__right__top__overview__right {
    display: flex;
    height: 34px;
    gap: 13px;
    justify-content: right;
}

.section__product__right__top__overview__right__wishlist {
    display: flex;
    font-family: Inter;
    font-size: 16px;
    font-weight: 600;
    line-height: 19.36px;
    text-align: left;
    color: rgba(212, 111, 119, 1);
    align-items: center;
    justify-content: center;
    gap: 10px;

}

.section__product__right__top__infos {
    display: flex;
    gap: 41px;
}


.section__product__right__top__infos__price h3 {
    font-family: Mulish;
    font-size: 34px;
    font-weight: 800;
    line-height: 42.67px;
    text-align: left;
    color: rgba(102, 99, 223, 1);

}

.section__product__right__top__infos__price h2 {
    font-family: Mulish;
    font-size: 21px;
    font-weight: 400;
    line-height: 26.36px;
    text-align: left;
    color: rgba(185, 187, 191, 1);

    text-decoration: line-through;
}

.section__product__right__top__infos__review p {
    font-family: Mulish;
    font-size: 14px;
    font-weight: 500;
    line-height: 17.57px;
    text-align: left;
    color: rgba(185, 187, 191, 1);

}

.section__product__right__top__infos__review p>span {
    color: rgba(62, 146, 66, 1);

}

.section__product__right__top__infos__review__item {
    display: flex;
    gap: 12px;
    justify-content: left;

}

.section__product__right__top__infos__review__item div {
    padding: 7px 10px 7px 10px;
    display: flex;
    gap: 7px;
    opacity: 0px;
    border-radius: 27px;
    font-family: Mulish;
    font-size: 14px;
    font-weight: 600;
    line-height: 17.57px;
    text-align: left;
    margin-bottom: 7px;
}

.section__product__right__top__infos__review__rating {
    background: rgba(251, 243, 234, 1);
    color: rgba(212, 141, 59, 1);
}

.section__product__right__top__infos__review__rating img {
    width: 45%;
    height: 85%;
}

.section__product__right__top__infos__review__rating span {
    padding-top: 17%;
}

.section__product__right__top__infos__review__feedback {
    background: rgba(237, 240, 248, 1);
    color: rgba(58, 73, 128, 1);

}

.section__product__right__top__infos__review__feedback span {
    padding-top: 6%;
}

.section__product__right__info {
    margin-top: 40px;
}

.section__product__right__info h3 {
    font-family: Inter;
    font-size: 16px;
    font-weight: 500;
    line-height: 19.36px;
    text-align: left;
    color: rgba(185, 187, 191, 1);
    margin-bottom: 13px;
}

.section__product__right__info__colors--select {
    display: flex;
    gap: 20px;
    margin-bottom: 40px;
}

.tick {
    width: 300%;
}

.section__product__right__info__colors--select button {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    display: block;
    padding: 17px;
    box-sizing: border-box;
    border: 3px solid white;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;

}

.label {
    font-family: Mulish;
    font-size: 14px !important;
    font-weight: 500;
    line-height: 17.57px;
    text-align: left;
    width: 114px;

}

.add {
    display: flex;
    gap: 20%;
}

.add button {
    background: rgba(102, 99, 223, 1);
    width: 310.87px;
    height: 59px;
    border-radius: 29.5px;
    opacity: 0px;
    font-family: Mulish;
    font-size: 20px;
    font-weight: 500;
    line-height: 20.08px;
    color: rgba(255, 255, 255, 1);
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;

}

.add_amount {
    display: flex;
    gap: 10px;
    align-items: center;
    font-family: Inter;
    font-size: 25px;
    font-weight: 900;
    line-height: 21.78px;
    color: rgba(163, 163, 163, 1);

}

.add_amount>span {
    cursor: pointer;
    display: flex;
    justify-content: center;
    width: 50px;
}

#amount {

    color: rgba(58, 73, 128, 1);
}

.add_amount>span:hover {
    color: rgba(58, 73, 128, 1);

}

.section__product__right {
    width: 40%;
}

.section__product__right__bottom {
    margin-top: 40px;
    border-radius: 14px;
    border: 1px solid rgba(228, 228, 228, 1);
    font-family: Mulish;
    font-size: 14px;
    font-weight: 500;
    line-height: 17.57px;
    text-align: left;
    color: rgba(114, 108, 108, 1);

}

.section__product__right__bottom h3 {
    font-size: 17px;
    font-weight: 800;
    line-height: 21.34px;
    color: rgba(29, 54, 77, 1);
}

.section__product__right__bottom a {
    padding: 0;
    color: rgba(114, 108, 108, 1);
    cursor: pointer;
    text-decoration: solid;
}

.section__product__right__bottom img {
    margin-top: 3px;
    height: 30%;
}

.section__product__right__bottom__free {
    border-bottom: 1px solid rgba(228, 228, 228, 1);
}

.section__product__right__bottom__free,
.section__product__right__bottom__return {
    display: flex;
    height: 48%;
    padding: 10px;
    gap: 14px;
}

/* end section product */

/* section body */
.section__body {
    font-family: Mulish;
    font-weight: 400;
    line-height: 20.08px;
    margin-top: 75px;

}

.tile {
    display: flex;
    flex-direction: row;
    text-align: left;
    gap: 30px;
    font-family: Mulish;
    font-weight: 600;
    border-bottom: 4px solid rgba(234, 236, 240, 1);
    color: rgba(152, 162, 179, 1);
}

.tile h3 {
    padding: 20px 23px;
}

.selected {
    color: rgba(22, 76, 150, 1);
    font-weight: 600px;
    border-bottom: 4px solid rgba(22, 76, 150, 1);
}

.content {
    margin: 25px auto;
    width: 92%;
    padding-left: 10px;
}

.content__list {
    margin: 25px 0;
}

.content__list img {
    margin-right: 1%;
}

.content h2 {
    display: inline-block;
    font-size: 24px;
    font-weight: 800;
    line-height: 30.12px;
    text-align: left;
    padding: 20px 0;
}

.content__description P {
    font-family: Mulish;
    font-size: 16px;
    font-weight: 400;
    line-height: 27.2px;
    text-align: left;

}

ul {
    list-style-type: none;
    margin-left: 10px;
}

ul li {
    margin-bottom: 12px;
    margin-left: -10px;
    display: flex;
    align-items: center;
}



.custom-rating .v-icon {
    color: #FFD700 !important;
    /* Màu vàng */
}

/* end section body */

/* section similar */
.section__similar {
    width: 100%;
    margin: 0 auto;


}

.section__similar__title {
    margin-top: 50px;
    font-family: Mulish;
    font-size: 30px;
    font-weight: 700;
    line-height: 38px;
    text-align: left;
}

.list_similar_product {
    position: relative;

}

@media (max-width: 767px) {
    .carousel-inner .carousel-item>div {
        display: none;
    }

    .carousel-inner .carousel-item>div:first-child {
        display: block;
    }
}

.carousel-inner .carousel-item.active,
.carousel-inner .carousel-item-next,
.carousel-inner .carousel-item-prev {
    display: flex;
}

/* medium and up screens */
@media (min-width: 768px) {

    .carousel-inner .carousel-item-end.active,
    .carousel-inner .carousel-item-next {
        transform: translateX(25%);
    }

    .carousel-inner .carousel-item-start.active,
    .carousel-inner .carousel-item-prev {
        transform: translateX(-25%);
    }
}

.carousel-inner .carousel-item-end,
.carousel-inner .carousel-item-start {
    transform: translateX(0);
}

.carousel-control-next,
.carousel-control-prev {
    top: 160% !important;

}

.lefticon {
    margin-top: 40%;
}

.righticon {
    margin-top: 40%;
}
</style>