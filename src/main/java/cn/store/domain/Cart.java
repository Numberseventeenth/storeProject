package cn.store.domain;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private double total = 0;

    Map<String,CartItem> map = new HashMap<>();

    public void clearCart(){
        map.clear();
    }

    public void removeCartItem(String pid){
        map.remove(pid);
    }

    public void addCartItemToCart(CartItem cartItem){
        String pid = cartItem.getProduct().getPid();
        if(map.containsKey(pid)){
            CartItem oldItem = map.get(pid);
            oldItem.setNum(oldItem.getNum() + cartItem.getNum());
        }else{
            map.put(pid,cartItem);
        }
    }

    public double getTotal(){
        total = 0;
        Collection<CartItem> values = map.values();
        for (CartItem cartItem : values) {
            total += cartItem.getSubTotal();
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Map<String, CartItem> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItem> map) {
        this.map = map;
    }

    public Collection<CartItem> getCartItems(){
        return map.values();
    }
}
