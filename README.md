# PhysicalObjectLayout

An Android layout that properly scales it's content in order to display physical objects such as credit cards, passports, etc.

![Example](https://user-images.githubusercontent.com/5675681/95358306-95d6c700-08d1-11eb-8c2a-1493baa0bfc7.jpg)

## What problem does it solve?

If you have ever tried to design and display a representation of physical world object, i.e. a credit card, 
in your Android app I’m pretty sure you have noticed, that the way of it appears on the screen is… different 🤔 

In Android we make fluid layouts. We specify sizes and margins in device-related units, set gravity for views 
and constraining them to each other to avoid overlaps. As a result, our layout looks good on different screens. 

But when it comes to displaying a physical object, things get messy:

![The problem](https://user-images.githubusercontent.com/5675681/95361727-c91b5500-08d5-11eb-9fcc-0f8b7f713a49.png)

Unlike our regular layouts, 
physical objects are not fluid, they must be proportionally scaled instead. 
When you figure this out you also realize, that such scaling of a layout is not an ordinary thing to do in Android. 
- You can’t use post-scaling because it looks blurry and requires drawing outside of the view frame
- You can’t just make this view a vector drawable because some of the elements have variable content
- Creating many-many dimens for every screen width is just crazy

## PhysicalOjectLayout comes to the rescue!

All you need to do is:
1. Create your layout with all sizes and margins fixed and set in `px`. 
Without any tricky layouts, without `dimens`, literaly a copy of what the designers sent to you
2. Put the view into the `PhysicalOjectLayout`
3. Enjoy your view properly scaled to fit by width or height, just like if it was an image

![Example](https://user-images.githubusercontent.com/5675681/95360658-5bbaf480-08d4-11eb-92ec-4bbc6debda14.png)

Describe attributes
Describe custom view scaling
Give Maven URL
