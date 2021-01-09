package com.yunxin.utils.security.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

class a {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private int e;
    private int f;
    private int g;
    private byte[] h;
    private boolean i = true;
    private int j;
    private Random random = new Random();

    a() {
    }

    private static long a(byte[] var0, int var1, int var2) {
        long var3 = 0L;
        if (var2 > 4) {
            var2 = var1 + 4;
        } else {
            var2 += var1;
        }

        while(var1 < var2) {
            var3 = var3 << 8 | (long)(var0[var1] & 255);
            ++var1;
        }

        return 4294967295L & var3;
    }

    private void a() {
        int var1;
        byte[] var2;
        for(this.f = 0; this.f < 8; ++this.f) {
            if (this.i) {
                var2 = this.a;
                var1 = this.f;
                var2[var1] = (byte)((byte)(var2[var1] ^ this.b[this.f]));
            } else {
                var2 = this.a;
                var1 = this.f;
                var2[var1] = (byte)((byte)(var2[var1] ^ this.c[this.e + this.f]));
            }
        }

        System.arraycopy(this.a(this.a), 0, this.c, this.d, 8);

        for(this.f = 0; this.f < 8; ++this.f) {
            var2 = this.c;
            var1 = this.d + this.f;
            var2[var1] = (byte)((byte)(var2[var1] ^ this.b[this.f]));
        }

        System.arraycopy(this.a, 0, this.b, 0, 8);
        this.e = this.d;
        this.d += 8;
        this.f = 0;
        this.i = false;
    }

    private byte[] a(byte[] var1) {
        int var2 = 16;

        label33: {
            long var3;
            long var5;
            long var9;
            long var11;
            long var13;
            long var15;
            boolean var10001;
            var3 = a(var1, 0, 4);
            var5 = a(var1, 4, 4);
            var9 = a(this.h, 0, 4);
            var11 = a(this.h, 4, 4);
            var15 = a(this.h, 8, 4);
            var13 = a(this.h, 12, 4);

            for(long var7 = 0L; var2 > 0; --var2) {
                var7 = var7 + (-1640531527L & 4294967295L) & 4294967295L;
                var3 = var3 + ((var5 << 4) + var9 ^ var5 + var7 ^ (var5 >>> 5) + var11) & 4294967295L;
                var5 = var5 + ((var3 << 4) + var15 ^ var3 + var7 ^ (var3 >>> 5) + var13) & 4294967295L;
            }

            try {
                ByteArrayOutputStream var17 = new ByteArrayOutputStream(8);
                DataOutputStream var20 = new DataOutputStream(var17);
                var20.writeInt((int)var3);
                var20.writeInt((int)var5);
                var20.close();
                var1 = var17.toByteArray();
                return var1;
            } catch (IOException var18) {
                var10001 = false;
            }
        }

        var1 = null;
        return var1;
    }

    private byte[] a(byte[] var1, int var2) {
        byte var3 = 16;

        label33: {
            long var4;
            long var8;
            long var10;
            long var12;
            long var14;
            long var16;
            boolean var10001;
            var4 = a(var1, var2, 4);
            var8 = a(var1, var2 + 4, 4);
            var14 = a(this.h, 0, 4);
            var16 = a(this.h, 4, 4);
            var12 = a(this.h, 8, 4);
            var10 = a(this.h, 12, 4);

            long var6 = -478700656L & 4294967295L;

            for(var2 = var3; var2 > 0; --var2) {
                var8 = var8 - ((var4 << 4) + var12 ^ var4 + var6 ^ (var4 >>> 5) + var10) & 4294967295L;
                var4 = var4 - ((var8 << 4) + var14 ^ var8 + var6 ^ (var8 >>> 5) + var16) & 4294967295L;
                var6 = var6 - (-1640531527L & 4294967295L) & 4294967295L;
            }

            try {
                ByteArrayOutputStream var21 = new ByteArrayOutputStream(8);
                DataOutputStream var18 = new DataOutputStream(var21);
                var18.writeInt((int)var4);
                var18.writeInt((int)var8);
                var18.close();
                var1 = var21.toByteArray();
                return var1;
            } catch (IOException var19) {
                var10001 = false;
            }
        }

        var1 = null;
        return var1;
    }

    private int b() {
        return this.random.nextInt();
    }

    private boolean b(byte[] var1, int var2, int var3) {
        boolean var5 = true;
        this.f = 0;

        while(true) {
            if (this.f >= 8) {
                this.b = this.b(this.b);
                if (this.b == null) {
                    var5 = false;
                } else {
                    this.j += 8;
                    this.d += 8;
                    this.f = 0;
                }
                break;
            }

            if (this.j + this.f >= var3) {
                break;
            }

            byte[] var6 = this.b;
            int var4 = this.f;
            var6[var4] = (byte)((byte)(var6[var4] ^ var1[this.d + var2 + this.f]));
            ++this.f;
        }

        return var5;
    }

    private byte[] b(byte[] var1) {
        return this.a(var1, 0);
    }

    private byte[] b(byte[] data, int start, int len, byte[] key) {
        this.a = new byte[8];
        this.b = new byte[8];
        this.f = 1;
        this.g = 0;
        this.e = 0;
        this.d = 0;
        this.h = key;
        this.i = true;
        this.f = (len + 10) % 8;
        if (this.f != 0) {
            this.f = 8 - this.f;
        }

        this.c = new byte[this.f + len + 10];
        this.a[0] = (byte)((byte)(this.b() & 248 | this.f));

        int var5;
        for(var5 = 1; var5 <= this.f; ++var5) {
            this.a[var5] = (byte)((byte)(this.b() & 255));
        }

        ++this.f;

        for(var5 = 0; var5 < 8; ++var5) {
            this.b[var5] = (byte)0;
        }

        this.g = 1;

        while(this.g <= 2) {
            if (this.f < 8) {
                key = this.a;
                var5 = this.f++;
                key[var5] = (byte)((byte)(this.b() & 255));
                ++this.g;
            }

            if (this.f == 8) {
                this.a();
            }
        }

        while(len > 0) {
            if (this.f < 8) {
                key = this.a;
                int var6 = this.f++;
                var5 = start + 1;
                key[var6] = (byte)data[start];
                --len;
                start = var5;
            }

            if (this.f == 8) {
                this.a();
            }
        }

        this.g = 1;

        while(this.g <= 7) {
            if (this.f < 8) {
                data = this.a;
                start = this.f++;
                data[start] = (byte)0;
                ++this.g;
            }

            if (this.f == 8) {
                this.a();
            }
        }

        return this.c;
    }

    protected byte[] a(byte[] var1, int var2, int var3, byte[] var4) {
        this.e = 0;
        this.d = 0;
        this.h = var4;
        var4 = new byte[var2 + 8];
        if (var3 % 8 == 0 && var3 >= 16) {
            this.b = this.a(var1, var2);
            this.f = this.b[0] & 7;
            int var6 = var3 - this.f - 10;
            if (var6 < 0) {
                var1 = null;
            } else {
                int var5;
                for(var5 = var2; var5 < var4.length; ++var5) {
                    var4[var5] = (byte)0;
                }

                this.c = new byte[var6];
                this.e = 0;
                this.d = 8;
                this.j = 8;
                ++this.f;
                this.g = 1;

                while(this.g <= 2) {
                    if (this.f < 8) {
                        ++this.f;
                        ++this.g;
                    }

                    if (this.f == 8) {
                        if (!this.b(var1, var2, var3)) {
                            var1 = null;
                            return var1;
                        }

                        var4 = var1;
                    }
                }

                var5 = var6;
                int var7 = 0;

                while(var5 != 0) {
                    int var8 = var7;
                    var6 = var5;
                    if (this.f < 8) {
                        this.c[var7] = (byte)((byte)(var4[this.e + var2 + this.f] ^ this.b[this.f]));
                        var8 = var7 + 1;
                        var6 = var5 - 1;
                        ++this.f;
                    }

                    var7 = var8;
                    var5 = var6;
                    if (this.f == 8) {
                        this.e = this.d - 8;
                        if (!this.b(var1, var2, var3)) {
                            var1 = null;
                            return var1;
                        }

                        var4 = var1;
                        var7 = var8;
                        var5 = var6;
                    }
                }

                this.g = 1;

                while(true) {
                    if (this.g >= 8) {
                        var1 = this.c;
                        break;
                    }

                    if (this.f < 8) {
                        if ((var4[this.e + var2 + this.f] ^ this.b[this.f]) != 0) {
                            var1 = null;
                            break;
                        }

                        ++this.f;
                    }

                    if (this.f == 8) {
                        this.e = this.d;
                        if (!this.b(var1, var2, var3)) {
                            var1 = null;
                            break;
                        }

                        var4 = var1;
                    }

                    ++this.g;
                }
            }
        } else {
            var1 = null;
        }

        return var1;
    }

    protected byte[] a(byte[] var1, byte[] var2) {
        return this.a(var1, 0, var1.length, var2);
    }

    protected byte[] b(byte[] data, byte[] key) {
        return this.b(data, 0, data.length, key);
    }
}
