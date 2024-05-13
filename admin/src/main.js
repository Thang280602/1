import App from '@/App.vue'
import vuetify from '@/plugins/vuetify'
import { loadFonts } from '@/plugins/webfontloader'
import router from '@/router'
import CKEditor from '@ckeditor/ckeditor5-vue'
import '@core/scss/template/index.scss'
import '@layouts/styles/index.scss'
import '@styles/styles.scss'
// import cors from 'cors'
// import express from 'express'
import { createPinia } from 'pinia'
import { createApp } from 'vue'
loadFonts()

// Create vue app
const app = createApp(App)

// const app1 = express();

// app1.use(cors({
//   origin: 'http://localhost:5174',
//   optionsSuccessStatus: 200 // Some legacy browsers choke on 204
// }));
// Use plugins
app.use(vuetify)
app.use(createPinia())
app.use(router)
app.use(CKEditor)

// Mount vue app
app.mount('#app')
