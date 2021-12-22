package graphics2d;

import java.util.LinkedList;

public class Scene2d {
    public DLinkList<SceneObject2d> allWorldObjects;


    Scene2d(){
        allWorldObjects=new DLinkList<SceneObject2d>();
    }

    public void addObject(SceneObject2d newObject){
        allWorldObjects.addLast(newObject);
    }

    public void listObjects(){
        DLinkList.DNode<SceneObject2d> cur= allWorldObjects.dummyHead.next;
        for(int i=0;i<allWorldObjects.size;i++){
            System.out.println(cur.element);
            cur=cur.next;
        }
    }
}
