package graphics2d;

import java.util.Iterator;

public class DLinkList<E> implements Iterable<E>{




    public DNode<E> dummyHead;
    public DNode<E> dummyTail;

    public int size;


    DLinkList(){
        dummyHead=new DNode<E>();
        dummyTail=new DNode<E>();

        dummyTail.prev=dummyHead;

        dummyHead.next=dummyTail;

        size=0;
    }

    public void addLast(E element){
        DNode<E> newDNode=new DNode<E>(element);


            newDNode.prev=dummyTail.prev;
            newDNode.next=dummyTail;
            dummyTail.prev.next=newDNode;
            dummyTail.prev=newDNode;


            size++;





    }

    public void addFirst(E element){
        DNode<E> newDNode=new DNode<E>(element);
        newDNode.next=dummyHead.next;
        newDNode.prev=dummyHead;
        dummyHead.next.prev=newDNode;
        dummyHead.next=newDNode;


        size++;

    }



    public DNode<E> removeLast(){
        if(size!=0){
            DNode<E> temp=dummyTail.prev;
            dummyTail.prev.prev.next=dummyTail;
            dummyTail.prev=dummyTail.prev.prev;
            size--;
            return temp;
        }
        return null;



    }

    public DNode<E> removeFirst(){
        if(size!=0){
            DNode<E> temp=dummyHead.next;
            dummyHead.next.next.prev=dummyHead;
            dummyHead.next=dummyHead.next.next;
            size--;
            return temp;
        }
        return null;
    }
    public void removeNode(){

    }

    public DNode<E> removeElement(E element){
        DNode<E> cur = dummyHead.next;


        while(cur!=dummyTail){

            if(cur.element==element) {

                cur.prev.next=cur.next;
                cur.next.prev=cur.prev;
                size--;
                return cur;
            }
                cur = cur.next;

        }
        return null;

    }

    public DNode<E> getNodeAt(int i){
        if(i<size) {
            if (i < (size / 2)) {
                DNode<E> cur = dummyHead.next;
                int j = 0;
                while (j < i) {
                    cur = cur.next;
                    j++;
                }
                return cur;

            } else {
                DNode<E> cur = dummyTail.prev;
                int j = size;
                while (j > i) {
                    cur = cur.prev;
                    j--;
                }
                return cur;
            }
        }else{return null;}


    }

    public DNode<E> getNodeAtElement(E element){
        DNode<E> cur = dummyHead.next;

        while(cur!=dummyTail){
            if(cur.element==element) {
                return cur;
            }
            cur = cur.next;

        }

        return null;

    }



    public DLIterator<E> iterator(){
        return new DLIterator<E>(this);
    }
    public class DLIterator<E> implements Iterator {
        DNode<E> cur;
        public DLIterator(DLinkList<E> c){
            cur=c.dummyHead.next;

        }
        public boolean hasNext(){
            return (cur !=dummyTail);
        }

        public E next(){
            DNode<E> tmp = cur;
            cur = cur.next;
            return tmp.element;
        }

    }





    public static class DNode<T>{
        public T element;
        public DNode<T> next;
        public DNode<T> prev;

        DNode(){

        }

        DNode(T element){
            this.element=element;
        }

        DNode(T element, DNode next){
            this.element=element;
            this.next=next;
        }
        DNode(T element, DNode<T> next, DNode<T> prev){
            this.element=element;
            this.next=next;
            this.prev=prev;
        }

    }
}




