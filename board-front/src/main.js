import { createApp } from 'vue'
import App from './App.vue'
import routers from './router/index.js' // [router]
import stores from './store/index.js' // [store[]

import "bootstrap/dist/css/bootstrap.min.css" // [bootstrap]
import "bootstrap" // [bootstrap]



const app = createApp(App);

app.config.globalProperties.$store = stores; 

app.use(routers);
app.use(stores);

app.config.globalProperties.$filters = {
  filterPrice(value) {
    if (!value) {
      return value;
    }
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  },
  enterToBr(value) {
    if (!value) {
      return value;
    }
    return value.replace(/(?:\r\n|\r|\n)/g, "<br />");
  },
};

app.mount('#app');