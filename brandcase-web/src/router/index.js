import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import test from '../views/test.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Home,
        // hidden: true
    },
    {
        path: '/test',
        name: 'test',
        component: test,
        // hidden: true
    },
]

const router = new VueRouter({
    routes
})

export default router
