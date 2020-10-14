# PhysicalObjectLayout

An Android layout that properly scales it's content in order to display physical objects such as credit cards, passports, etc.

![Example](https://user-images.githubusercontent.com/5675681/95358306-95d6c700-08d1-11eb-8c2a-1493baa0bfc7.jpg)

## Dependency
Ensure that you have JitPack repo added to your project `build.gradle` file:

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the library to your app `build.gradle` file:

```gradle
dependencies {
	implementation 'com.github.radiokot:android-physical-object-layout:-SNAPSHOT'
}
```
## What problem does it solve?

If you have ever tried to design and display a representation of physical world object, i.e. a credit card, 
in your Android app I’m pretty sure you have noticed, that the way it appears on the screen is… different 🤔 

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

## PhysicalObjectLayout comes to the rescue!

All you need to do is:
1. Create your layout with all sizes and margins fixed and set in `px`. 
Without any tricky layouts, without `dimens`, literally a copy of what the designers sent to you
2. Put the view into the `PhysicalObjectLayout`
3. Enjoy your view properly scaled to fit the container width or height, just like if it was an image

![Example](https://user-images.githubusercontent.com/5675681/95360658-5bbaf480-08d4-11eb-92ec-4bbc6debda14.png)

## Layout attributes
`PhysicalObjectLayout` has a few attributes that controls scaling and appearance:

| Name | Description | Default |
|------|-------------|---------|
| `pol_scaleBy` | Specifies a dimension that children will be scaled to fit to | `width` |
| `pol_addChildrenInvisible` | If enabled the layout will set it's children visibility to `View.INVISIBLE` on add. As soon as the scaling is not instant it is recommended to keep this attribute enabled in order to avoid unscaled child blink | `true` |
| `pol_makeChildrenVisibleAfterScale` | If enabled the layout will set it's children visibility to `View.VISIBLE` once they are scaled. Recommended to use in a combination with the previous attribute  | `true` |

## Scaling custom views

`PhysicalObjectLayout` allows you to specify custom scaling strategies in order to scale custom views. 

You can implement your own `ViewScalingStrategy` and apply it programmatically using `addScalingStrategies`
or `addScalingStrategy` methods.

Examples of custom scaling strategies can be found in the library itself:
[CardViewScalingStrategy](physicalobjectlayout/src/main/java/ua/com/radiokot/physicalobjectlayout/scalingstrategy/CardViewScalingStrategy.kt),
[TextViewScalingStrategy](physicalobjectlayout/src/main/java/ua/com/radiokot/physicalobjectlayout/scalingstrategy/TextViewScalingStrategy.kt)

## License

### The MIT License

```
Copyright (c) 2020 Oleg Koretsky

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the “Software”), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
```