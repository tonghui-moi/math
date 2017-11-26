package com.laitech.mathboard;

import java.util.ArrayList;
import java.util.List;
/**
*	The following operators are supported:
*	+ - * / sin cos tan ln ^ abs asin acos atan
*	If the expression contains any other characters rather than number,x,e,
*	PI,() and the above operators or the expression is wrong,this will throw an ExpressionErrorException
*/
class ExpressionErrorException extends Exception{
	private static final long serialVersionUID = 1L;}
class Pro {
	float val;
	int arg, type;
	Object obj;
}
public class UnitPro extends Pro {
	private final static int num = 0;
	private final static int op1 = 1;
	private final static int op2 = 2;
	private final static int op3 = 3;
	private final static int cos = 5;
	private final static int sin = 6;
	private final static int tan = 7;
	private final static int ln = 8;
	private final static int abs = 9;
	private final static int acos = 10;
	private final static int asin = 11;
	private final static int atan = 12;
	private Pro[] pro1;
	private Pro[] pro2;
	private Pro[][] pro3;
	private Pro[] pro4;
	private float[] vals;
	private UnitPro[] upro;
	private Boolean[] op;
	public float cal(float x) {
		for (Pro p : pro1) {
			p.val = x;
		}
		for (UnitPro p : upro) {
			p.cal(x);
		}
		for (Pro p : pro2) {
			switch (p.type) {
			case cos:
				p.val = (float) Math.cos(((Pro) p.obj).val);
				break;
			case sin:
				p.val = (float) Math.sin(((Pro) p.obj).val);
				break;
			case tan:
				p.val = (float) Math.tan(((Pro) p.obj).val);
				break;
			case acos:
				p.val = (float) Math.acos(((Pro) p.obj).val);
				break;
			case asin:
				p.val = (float) Math.asin(((Pro) p.obj).val);
				break;
			case atan:
				p.val = (float) Math.atan(((Pro) p.obj).val);
				break;
			case ln:
				p.val = (float) Math.log(((Pro) p.obj).val);
				break;
			case abs:
				p.val = Math.abs(((Pro) p.obj).val);
				break;
			}
		}
		if (pro4.length != 0) {
			Pro[] ps;
			for (Pro p : pro4) {
				ps = (Pro[]) p.obj;
				p.val = (float) Math.pow(ps[0].val, ps[1].val);
			}
		}
		float temp = 0;
		int i = 0;
		int j = 0;
		for (Pro[] ps : pro3) {
			temp = ps[0].val;
			for (i = 1; i < ps.length; i++) {
				if (ps[i].arg == 2)
					temp *= ps[i].val;
				else
					temp /= ps[i].val;
			}
			vals[j++] = temp;
		}
		temp = 0;
		j = 0;
		for (boolean o : op) {
			if (o)
				temp += vals[j];
			else
				temp -= vals[j];
			j++;
		}
		this.val = temp;
		return val;
	}
	private int getType(int t)
    {
        if(t==num)
            return 0;
        if(t<=op3)
            return 1;
        return 2;
    }
	public void parse(String str) throws ExpressionErrorException {
		if(str.isEmpty())
			throw new ExpressionErrorException();
		this.type=num;
		str=str.toLowerCase();
		List<Pro> ps1, ps2, ps4;
		ps1 = new ArrayList<Pro>();
		ps2 = new ArrayList<Pro>();
		ps4 = new ArrayList<Pro>();
		List<UnitPro> up1;
		up1 = new ArrayList<UnitPro>();
		char[] cs = str.toCharArray();
		char c;
		List<Pro> l = new ArrayList<Pro>();
		int lastType=-1,reqType=-1,type;
		Pro p=null;
		for (int i = 0; i < cs.length; i++) {
			p= new Pro();
			c = cs[i];
			if (c > 47 && c < 58) {
				p.type = num;
				while (c > 47 && c < 58) {
					p.val = p.val * 10 + c - 48;
					i++;
					if (i == cs.length)
						break;
					c = cs[i];
				}
				if (c == '.') {
					c = cs[++i];
					int ii = 1;
					while (c > 47 && c < 58) {
						p.val += (c - 48) * Math.pow(0.1, ii++);
						i++;
						if (i == cs.length)
							break;
						c = cs[i];
					}
				}
				if (i != cs.length)
					i--;
			} else if (c == '(') {
				String ss = "";
				int j = 1;
				while (true) {
					if (++i == cs.length)
						throw new ExpressionErrorException();
					c = cs[i];
					if (c == ')')
						j--;
					else if (c == '(')
						j++;
					if(j==0)
						break;
					ss += c;
				}
				UnitPro n = new UnitPro();
				n.parse(ss);
				p = n;
				up1.add(n);
			} else {
				switch (c) {
				case 'x':
					p.type = num;
					ps1.add(p);
					break;
				case 'e':
					p.type = num;
					p.val = (float) Math.E;
					break;
				case 'p':
					if(cs[++i]!='i')
						throw new ExpressionErrorException();
					p.type = num;
					p.val = (float) Math.PI;
					break;
				case 'c':
					if(cs[++i]=='o'&&cs[++i]=='s')
						p.type = cos;
					else
						throw new ExpressionErrorException();
					break;
				case 's':
					if(cs[++i]=='i'&&cs[++i]=='n')
						p.type = sin;
					else
						throw new ExpressionErrorException();
					break;
				case 't':
					if(cs[++i]=='a'&&cs[++i]=='n')
						p.type = tan;
					else
						throw new ExpressionErrorException();
					break;
				case 'l':
					if(cs[++i]=='n')
						p.type = ln;
					else
						throw new ExpressionErrorException();
					break;
				case 'a':
					if(cs[i+1]=='b')
					{
						i++;
						if(cs[++i]=='s')
						{
							p.type=abs;
						}
						else
							throw new ExpressionErrorException();
					}
					else if(cs[i+1]=='s')
					{
						i++;
						if(cs[++i]=='i'&&cs[++i]=='n')
							p.type=asin;
						else
							throw new ExpressionErrorException();
					}
					else if(cs[i+1]=='c')
					{
						i++;
						if(cs[++i]=='o'&&cs[++i]=='s')
							p.type=acos;
						else
							throw new ExpressionErrorException();
					}
					else if(cs[i+1]=='t')
					{
						i++;
						if(cs[++i]=='a'&&cs[++i]=='n')
							p.type=atan;
						else
							throw new ExpressionErrorException();
					}
					else
						throw new ExpressionErrorException();
					break;
				case '+':
					p.type = op1;
					p.arg = 0;
					break;
				case '-':
					p.type = op1;
					p.arg = 1;
					break;
				case '*':
					p.type = op2;
					p.arg = 2;
					break;
				case '/':
					p.type = op2;
					p.arg = 3;
					break;
				case '^':
					p.type = op3;
					ps4.add(p);
					break;
				default:
					throw new ExpressionErrorException();
				}
			}
			if (p.type > 4 && p.type < 13)
				ps2.add(p);
			l.add(p);

			type=getType(p.type);
            if(lastType!=-1&&type==lastType)
                throw new ExpressionErrorException();
            lastType=type;
            if(type==0)
            {
                if(reqType!=-1&&reqType!=0)
                    throw new ExpressionErrorException();
                reqType=1;
            }
            else if(type==1)
            {
                if(reqType!=-1&&reqType!=1)
                    throw new ExpressionErrorException();
                reqType=-1;
            }
            else
            {
                if(reqType!=-1&&reqType!=2)
                    throw new ExpressionErrorException();
                reqType=0;
            }
		}
		if(p.type!=num)
			throw new ExpressionErrorException();
		int i = 0;
		int id;
		while (i < ps2.size()) {
			p = ps2.get(i);
			id = l.indexOf(p) + 1;
			p.obj = l.get(id);
			l.remove(id);
			i++;
		}
		if (ps4.size() != 0) {
			Pro[] pros = new Pro[2];
			i = 1;
			p = ps4.get(0);
			id = l.indexOf(p) - 1;
			pros[0] = l.get(id);
			pros[1] = l.get(id + 2);
			p.obj = pros;
			l.remove(id);
			l.remove(id + 1);
			while (i < ps4.size()) {
				pros = new Pro[2];
				p = ps4.get(i);
				if (l.indexOf(p) - 1 == id)
					throw new ExpressionErrorException();
				id = l.indexOf(p) - 1;
				pros[0] = l.get(id);
				pros[1] = l.get(id + 2);
				p.obj = pros;
				l.remove(id);
				l.remove(id + 1);
				i++;
			}
		}
		i = -1;
		int next;
		List<Pro[]> ps3;
		List<Pro> temp = null;
		ps3 = new ArrayList<Pro[]>();
		List<Boolean> ops = new ArrayList<Boolean>();
		temp = new ArrayList<Pro>();
		if ((next = l.get(0).type) == op1) {
			ops.add(l.get(0).arg == 0);
			temp.add(l.get(1));
			i = 0;
		} else if (next == op2)
			throw new ExpressionErrorException();
		else {
			ops.add(true);
			temp.add(l.get(0));
		}
		while ((i += 2) < l.size()) {
			p = l.get(i);
			next = p.arg;
			if (p.type == op1) {
				ops.add(next == 0);
				ps3.add(temp.toArray(new Pro[0]));
				temp = new ArrayList<Pro>();
				temp.add(l.get(i + 1));
			} else {
				p = l.get(i + 1);
				p.arg = next;
				temp.add(p);
			}
		}
		ps3.add(temp.toArray(new Pro[0]));
		pro1 = ps1.toArray(new Pro[0]);
		pro2 = ps2.toArray(new Pro[0]);
		pro3 = ps3.toArray(new Pro[0][0]);
		pro4 = ps4.toArray(new Pro[0]);
		op = ops.toArray(new Boolean[0]);
		upro = up1.toArray(new UnitPro[0]);
		vals = new float[pro3.length];
	}
}
