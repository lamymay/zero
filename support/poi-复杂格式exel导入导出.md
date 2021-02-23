

poi 颜色问题

https://blog.csdn.net/z1074907546/article/details/50544178/


#手动获取颜色

red - the RGB red component, between 0 and 255 inclusive
green - the RGB green component, between 0 and 255 inclusive
blue - the RGB blue component, between 0 and 255 inclusive

````java
      //    public final static Color orange    = new Color(255, 200, 0);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFColor color = wb.getCustomPalette().findColor((byte) 255, (byte) 200, (byte) 0);

```
